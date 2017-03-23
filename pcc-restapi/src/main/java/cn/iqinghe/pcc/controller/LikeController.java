package cn.iqinghe.pcc.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import cn.iqinghe.pcc.domain.User;
import cn.iqinghe.pcc.result.LikeOpsResult;
import cn.iqinghe.pcc.result.LikeUsersResult;
import cn.iqinghe.pcc.service.LikeService;

/**
 * like restapi
 * 
 * @author wallechen
 *
 */
@RestController
@RequestMapping("/likes")
public class LikeController {
	private Long currentUserId = 1L;
	@Autowired
	private LikeService likeService;

	@RequestMapping(value = "/{feedId}", method = RequestMethod.GET)
	public @ResponseBody LikeUsersResult listLikeUsers(@PathVariable("feedId") Long feedId,
			@RequestParam(value = "cursor", required = false) int cursor,
			@RequestParam(value = "page_size", required = false) int pageSize) {
		LikeUsersResult result = new LikeUsersResult();
		result.setOid(feedId);
		if (feedId == null) {
			return result;
		}
		List<User> users = likeService.likeUsers(cursor, pageSize, feedId);
		result.setLike_list(users);
		return result;
	}

	@RequestMapping(value = "/{feedId}", method = RequestMethod.PUT)
	public @ResponseBody LikeOpsResult like(@PathVariable("feedId") Long feedId,
			@RequestParam(value = "uid", required = false) Long uid) {
		LikeOpsResult likeOpsResult = new LikeOpsResult();
		likeOpsResult.setOid(feedId);
		likeOpsResult.setUid(uid);
		// 当前用户参数可传入，方便测试，如果未传入，则取默认值，实际操作过程中，通过当前会话获取
		if (feedId == null) {
			return likeOpsResult;
		}
		long userId = currentUserId;
		if (uid != null) {
			userId = uid;
		}
		long result = likeService.like(feedId, userId);
		if (result > 0) {
			List<User> users = likeService.likeUsers(0, 20, feedId);
			likeOpsResult.setLike_list(users);
		}
		return likeOpsResult;
	}

	@RequestMapping(value = "/{feedId}", method = RequestMethod.DELETE)
	public @ResponseBody LikeOpsResult unlike(@PathVariable("feedId") Long feedId,
			@RequestParam(value = "uid", required = false) Long uid) {
		LikeOpsResult likeOpsResult = new LikeOpsResult();
		likeOpsResult.setOid(feedId);
		likeOpsResult.setUid(uid);
		// 当前用户参数可传入,方便测试，如果未传入，则取默认值，实际操作过程中，通过当前会话获取
		if (feedId == null) {
			return likeOpsResult;
		}
		long userId = currentUserId;
		if (uid != null) {
			userId = uid;
		}
		long result = likeService.unlike(feedId, userId);
		if (result >= 0) {
			List<User> users = likeService.likeUsers(0, 20, feedId);
			likeOpsResult.setLike_list(users);
		}
		return likeOpsResult;
	}
}
