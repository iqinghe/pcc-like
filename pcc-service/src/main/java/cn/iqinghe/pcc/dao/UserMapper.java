package cn.iqinghe.pcc.dao;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import cn.iqinghe.pcc.domain.User;

/**
 * 用户持久化服务，由于是demo，只提供查询
 * 
 * @author wallechen
 *
 */
@Repository
public interface UserMapper {
	User getById(@Param("uid") Long uid);
}
