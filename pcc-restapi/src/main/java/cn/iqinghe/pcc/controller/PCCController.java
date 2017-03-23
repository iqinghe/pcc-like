package cn.iqinghe.pcc.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import cn.iqinghe.pcc.domain.User;
import cn.iqinghe.pcc.result.LikeCountResult;
import cn.iqinghe.pcc.result.LikeOpsResult;
import cn.iqinghe.pcc.result.LikeUsersResult;
import cn.iqinghe.pcc.result.LikedResult;
import cn.iqinghe.pcc.service.LikeService;

/**
 * pcc like restapi
 * 
 * @author wallechen
 *
 */
@RestController
@RequestMapping("/pcc")
public class PCCController {
	@Autowired
	private LikeService likeService;

	@RequestMapping(value = "/test", method = RequestMethod.GET)
	public String test() {
		return "test";
	}

	@RequestMapping(method = RequestMethod.GET)
	public @ResponseBody Object pcc(@RequestParam(value = "action", required = false) String action,
			@RequestParam(value = "oid", required = false) Long oid,
			@RequestParam(value = "uid", required = false) Long uid,
			@RequestParam(value = "cursor", required = false) Integer cursor,
			@RequestParam(value = "page_size", required = false) Integer pageSize,
			@RequestParam(value = "is_friend", required = false) Integer friend) {
		if (StringUtils.isEmpty(action)) {
			return null;
		}
		action = action.toLowerCase();
		switch (action) {
		case "like": {
			return this.like(oid, uid);
		}
		case "is_like": {
			return this.isLiked(oid, uid);
		}
		case "count": {
			return this.likeCount(oid);
		}
		case "list": {
			if (cursor == null) {
				cursor = 0;
			}
			if (pageSize == null) {
				pageSize = 20;
			}
			if (friend == null) {
				friend = 0;
			}
			return this.likeList(oid, uid, cursor, pageSize, 1 == friend ? true : false);
		}
		default:
			return null;
		}
	}

	private LikeOpsResult like(long oid, long uid) {
		LikeOpsResult likeOpsResult = new LikeOpsResult();
		long result = likeService.like(oid, uid);
		if (result > 0) {
			List<User> users = likeService.likeUsers(0, 20, oid);
			likeOpsResult.setOid(oid);
			likeOpsResult.setUid(uid);
			likeOpsResult.setLike_list(users);
		}
		return likeOpsResult;
	}

	private LikedResult isLiked(long oid, long uid) {
		LikedResult result = new LikedResult();
		boolean liked = likeService.hasLiked(oid, uid);
		result.setOid(oid);
		result.setUid(uid);
		result.setIs_like(liked ? 1 : 0);
		return result;
	}

	private LikeCountResult likeCount(long oid) {
		LikeCountResult result = new LikeCountResult();
		result.setOid(oid);
		result.setCount(likeService.likeUserCount(oid));
		return result;
	}

	private LikeUsersResult likeList(long oid, long uid, int cursor, int pageSize, boolean isFriend) {
		LikeUsersResult result = new LikeUsersResult();
		result.setOid(oid);
		result.setNext_cursor(cursor + 1);
		if (isFriend) {
			// result.setLike_list(likeService.likeUsersWithFriendsFirst(cursor,
			// pageSize, oid, uid));
		} else {
			int offset = cursor * pageSize;
			result.setLike_list(likeService.likeUsers(offset, pageSize, oid));
		}
		return result;
	}
}
