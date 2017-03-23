package cn.iqinghe.pcc.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import cn.iqinghe.pcc.domain.Feed;

/**
 * feed持久化服务，由于是demo，只提供查询服务
 * 
 * @author wallechen
 *
 */
@Repository
public interface FeedMapper {
	Feed getById(@Param("id") Long feedId);

	List<Feed> listFeeds(@Param("uid") Long uid);
}
