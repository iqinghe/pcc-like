package cn.iqinghe.pcc.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import cn.iqinghe.pcc.domain.Like;

/**
 * like 持久化
 * 
 * @author wallechen
 *
 */
@Repository
public interface LikeMapper {
	int insert(Like like);

	int delete(@Param("id") Long id);

	int updateByPrimaryKey(Like like);

	int updateByPrimaryKeySelective(Like like);

	List<Like> listFeedLikes(@Param("feedId") Long feedId);

	Like getLike(@Param("feedId") Long feedId, @Param("uid") Long uid);
}
