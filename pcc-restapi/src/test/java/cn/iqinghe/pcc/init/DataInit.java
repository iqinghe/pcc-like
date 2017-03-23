package cn.iqinghe.pcc.init;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DataInit {
	private int dataSize = 100000;
	private int batchSize = 50000;
	@Autowired
	private JdbcTemplate jdbcTemplate;

	// @Test
	public void initTest() {
		long start = System.currentTimeMillis();
		int times = dataSize / batchSize;
		for (int i = 0; i < times; i++) {
			StringBuilder sb = new StringBuilder();
			List<Object[]> params = new ArrayList<Object[]>(batchSize);
			for (int j = i * batchSize + 1; j <= (i + 1) * batchSize; j++) {
				params.add(new Object[] { "uname" + j, "name" + j, new Date(), "" });
			}
			sb.append("INSERT INTO `test`.`p_user`(`username`,`nickname`,`create_date`,`avatar`) VALUES (?,?,?,?);");
			jdbcTemplate.batchUpdate(sb.toString(), params);
		}
		long end = System.currentTimeMillis();
		long excuteTime = (end - start) / 1000;
		System.out.println("time is:" + excuteTime + " s");
		System.out.println("qps is:" + batchSize / excuteTime + "t/s");
	}

}
