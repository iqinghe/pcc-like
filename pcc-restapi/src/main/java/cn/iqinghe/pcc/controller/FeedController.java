package cn.iqinghe.pcc.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import cn.iqinghe.pcc.domain.Feed;
import cn.iqinghe.pcc.result.FeedResult;
import cn.iqinghe.pcc.service.FeedService;

/**
 * feed restapi
 * 
 * @author wallechen
 *
 */
@RestController
@RequestMapping("/feeds")
public class FeedController {
	private Long currentUserId = 1L;
	@Autowired
	private FeedService feedService;

	@RequestMapping(value = "/mine", method = RequestMethod.GET)
	public @ResponseBody FeedResult listMyFeeds(@RequestParam(value = "uid", required = false) Long uid) {
		// 当前用户参数可传入，方便测试，如果未传入，则取默认值，实际操作过程中，通过当前会话获取
		long userId = currentUserId;
		if (uid != null) {
			userId = uid;
		}
		List<Feed> feeds = feedService.listFeeds(0, 100, userId);
		return new FeedResult(0, feeds);
	}

	@RequestMapping(value = "/{feedId}", method = RequestMethod.GET)
	public @ResponseBody Feed getFeed(@PathVariable("feedId") Long feedId) {
		if (feedId == null) {
			return null;
		}
		return feedService.getFeed(feedId);
	}
}
