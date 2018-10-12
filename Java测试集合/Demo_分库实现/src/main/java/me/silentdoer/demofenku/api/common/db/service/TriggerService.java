package me.silentdoer.demofenku.api.common.db.service;

/**
 * 这一层的service主要是那种如触发器、存储过程的service，因为互联网圈子的代码一般不会用触发器、存储过程、外键等；
 * 所以这些实现都考代码，而这一部分的实现则是放在当前层；比如之前update UserChangePhoneLog和update user.Cellphone是在
 * 用户换号成功后需要同时执行完毕的，这一部分其实就类似触发器，如update UserChangePhoneLog的同时触发update user.Cellphone
 *
 * @author liqi.wang
 * @version 1.0.0
 * @date 8/22/2018 2:35 PM
 */
public class TriggerService {
}
