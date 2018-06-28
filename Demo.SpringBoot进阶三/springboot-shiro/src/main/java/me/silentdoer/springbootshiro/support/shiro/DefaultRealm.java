package me.silentdoer.springbootshiro.support.shiro;

import lombok.experimental.var;
import lombok.val;
import lombok.extern.slf4j.Slf4j;
import me.silentdoer.springbootshiro.model.UserInfo;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.apache.shiro.web.filter.authc.AnonymousFilter;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.apache.shiro.web.filter.authz.RolesAuthorizationFilter;

import java.util.*;

/**
 * Realm类似是专用于安全框架的DAO，Shiro的认证过程最终会交由Realm执行；
 * 即Realm内部获取用户的权限信息进行判断？
 *
 * 由getAuthenticationInfo(token)方法来进行验证，其主要功能：
 * 检查提交的进行认证的令牌信息（这个token是怎么生成的呢？来自客户端的如header附带？）
 * 根据令牌信息从数据源(通常为数据库)中获取用户信息（所以token其实是类似用jwt生成的？）
 * 对用户信息进行匹配验证。（TODO 这一步是关键，看下他们是做哪些工作）
 * 验证通过将返回一个封装了用户信息的AuthenticationInfo实例。
 * 验证失败则抛出AuthenticationException异常信息。
 *
 *
 * Realm类用于自定义如何查询用户信息，如何查询用户的角色和权限，如何校验密码等逻辑
 *
 * TODO realm 读 re l m，领域，范围的意思
 *
 * @author liqi.wang
 * @version 1.0.0
 */
public class DefaultRealm extends AuthorizingRealm {

    /**
     * 告诉shiro如何根据获取到的用户信息中的密码和盐值来校验密码
     */
    {
        // 设置用于匹配密码的CredentialsMatcher，来自shiro中的类
        HashedCredentialsMatcher hashMatcher = new HashedCredentialsMatcher();
        hashMatcher.setHashAlgorithmName("md5");
        hashMatcher.setStoredCredentialsHexEncoded(true);
        // 散列的次数
        hashMatcher.setHashIterations(1024);
        this.setCredentialsMatcher(hashMatcher);
    }

    /**
     * 定义如何  获取用户的角色和权限  的逻辑，给shiro做  权限判断
     * TODO 这个方法和下面的方法是当被Filter如FormAuthenticationFilter 拦截到后会从如session里获取authenticationToken对象然后
     *
     * authorization授权
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        if (principalCollection == null) {
            throw new AuthorizationException("PrincipalCollection method argument cannot be null.");
        }
        UserInfo user = (UserInfo) this.getAvailablePrincipal(principalCollection);
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        info.setRoles(user.getRoles());
        info.setStringPermissions(user.getPerms());
        return info;
    }

    /**
     * 定义如何  获取用户信息  的业务逻辑，给shiro做  登录
     *
     * TODO 终于有点明白了，这个方法要生效是要在 /login接口里用SecurityUtils.getSubject().login(new UsernamePasswordToken(userName, password));
     * TODO 然后该login方法最终会执行到这里，然后这里进行判断username和password是否匹配，发现不匹配或者压根没有则抛出异常，然后/login里捕获做处理（可以继续上抛给全局异常
     * TODO 处理器去处理）
     *
     * TODO 如果这里验证登陆成功，则将登陆者的 角色权限等信息 通过返回一个AuthenticationInfo来缓存；之后就可以通过SecurityUtils.getSubject().getPrincipal()来获取
     *
     * 注意，SimpleAuthenticationInfo和UsernamePasswordToken都实现了AuthenticationInfo接口；
     *
     * authentication 认证
     *
     * 然后通过FormAuthenticationFilter就会通过对方传来的SESSIONID来获取对应的Subject对象（保存有principals），如果存在说明登录成功
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        // UsernamePasswordToken其实就可以理解为username和password的持有对象（holder）
        UsernamePasswordToken upToken = (UsernamePasswordToken) authenticationToken;
        String username = upToken.getUsername();
        if (username == null) {
            throw new AccountException("Null usernames are not allowed by this realm.");
        }
        //UserInfo userDB = userService.selectBy("username",username);
        UserInfo userDB = UserInfo.builder().build();
        if (Objects.isNull(userDB)) {
            throw new UnknownAccountException("No account found for admin [" + username + "]");
        }
        //查询用户的角色和权限存到SimpleAuthenticationInfo中，这样在其它地方

        // 先要SecurityUtils.getSubject().login();才能getPrincipal()
        //SecurityUtils.getSubject().getPrincipal();  // 也是Shiro的
        //SecurityUtils.getSubject().getPrincipal()就能拿出用户的所有信息，包括角色和权限
        //List<String> roleList = userRoleService.getRolesByUserId(userDB.getId());
        //List<String> permList = rolePermService.getPermsByUserId(userDB.getId());
        List<String> roleList = Collections.emptyList();
        List<String> permList = Collections.emptyList();
        Set<String> roles = new HashSet<String>(roleList);
        Set<String> perms = new HashSet<String>(permList);
        userDB.setRoles(roles);
        userDB.setPerms(perms);

        // principal（主要）似乎必须要提供getRoles和getPerms两个方法？？，credential（凭据），realmName
        SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(userDB, userDB.getPassword(), getName());
        info.setCredentialsSalt(ByteSource.Util.bytes(userDB.getSalt()));
        return info;
    }
}
