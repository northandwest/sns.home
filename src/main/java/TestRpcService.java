import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.bucuoa.ucenter.api.UserRpcService;
import com.bucuoa.ucenter.model.UlewoUser;

public class TestRpcService {
	
	public static void main(String[] args)
	{
		ApplicationContext ctx = new ClassPathXmlApplicationContext("classpath*:spring.xml");
		com.bucuoa.ucenter.api.UserRpcService service = (UserRpcService) ctx.getBean("userRpcService");
		UlewoUser user = service.findUserByEmail("9760976");
		
		System.out.println("nickname=>"+user.getNickName());
	}

}
