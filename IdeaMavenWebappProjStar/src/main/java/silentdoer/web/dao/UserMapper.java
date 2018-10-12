// package silentdoer.web.dao;
//
//
// import org.apache.ibatis.annotations.Param;
// import org.apache.ibatis.session.RowBounds;
// import silentdoer.web.entity.User;
//
// import java.util.List;
//
// public interface UserMapper {
//
//     int insert(User user);
//
//     User selectByPhone(@Param("fCellphone") String fCellphone);
//
//     User selectById(Integer fId);
//
//     User selectUserByIDCard(String fidCard);
//
//     Integer selectByIdCard(@Param("fIdCard") String fIdCard);
//
//     int updateById(User record);
//
//     User selectByTaofen8UserId(String fTaofen8UserId);
//
//     Integer selectUserNameCount(String userName);
//
//     User selectByPhoneOrName(String account);
//
//     User selectByInviteCode(String inviteCode);
//
//     int updateInviteCode(User user);
//
//     int updateAddress(User user);
//
//     int updateTreasureFlag(UserTreasureFlag user);
//
//     int updateAvatar(User user);
//
//     int updateCellphone(User user);
//
//     int unBindTaofen8Id(User user);
//
//     int updateUserStatus(User user);
//
//     int updateFinancialInvestType(User user);
//
//     Integer registerNumer(@Param("deadline") String deadline);
//
//     /**
//      * 筛选已实名用户ID列表
//      *
//      * @return
//      */
//     List<Integer> selectVerifiedUserIdList();
//
//     /**
//      * 根据ID筛选淘粉吧用户(f_taofen8_user_id不为空)
//      */
//     List<SimpleFieldQueryModel> selectTaofen8BindUserByUids(List<Integer> uids);
//
//     /**
//      * 获得淘粉吧用户指定日期的用户和绑卡时间
//      */
//     List<SimpleFieldQueryModel> getTaofen8UserAndBindTimeByDate(String date);
//
//     List<User> birthdayList(@Param("flag") Integer flag, @Param("searchStr") String searchStr);
//
//     List<SpringFestivalWhiteList> springfestivalInviteWhiteList();
//
//     List<User> myInviteList(String userId);
//
//     PageList<User> myInviteListWithPage(PageBounds pageBounds, @Param("userId") String userId);
//
//     Integer allMyInviteUserNum(String userId);
//
//     /**
//      * 筛选淘粉吧绑定的实名用户-分页
//      *
//      * @param pageBounds
//      * @return
//      */
//     List<SimpleFieldQueryModel> selectAllBindCardTaofen8User(PageBounds pageBounds);
//
//     /**
//      * 获取所有绑卡用户，所有进一步的判断由程序处理；如是否是注册日三十天
//      *
//      * @param pageBounds
//      * @return
//      */
//     List<IdDatetimeModel> selectAllBindCardUserWithRegisterTime(PageBounds pageBounds);
//
//     /**
//      * 获取最新注册的有效用户
//      */
//     List<IdDatetimeModel> selectAllNewUserWithRegisterTime(PageBounds pageBounds);
//
//     /**
//      * 用于测试，测试完毕后会删除
//      */
//     List<IdDatetimeModel> forTest(@Param("start") Integer start, @Param("length") Integer length);
//
//     List<IdDatetimeModel> forTest2();
//
//     List<IdDatetimeModel> forTest3(RowBounds pageBounds);
// }