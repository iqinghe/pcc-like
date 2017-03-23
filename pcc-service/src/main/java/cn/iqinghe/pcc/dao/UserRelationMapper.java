package cn.iqinghe.pcc.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import cn.iqinghe.pcc.domain.UserRelation;

/**
 * 用户关系持久化，由于是demo，只提供查询服务
 * 
 * @author wallechen
 *
 */
@Repository
public interface UserRelationMapper {
	List<UserRelation> listUserFriends(@Param("uid") Long uid);
}
