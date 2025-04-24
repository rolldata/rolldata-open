package com.rolldata.core.common.listener;


import com.rolldata.web.system.service.ScheduleJobService;
import com.rolldata.web.system.service.SystemService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

/**
 * 
 * @Title:WebStartInitListener
 * @Description:程序启动完成执行
 * @Company:www.wrenchdata.com
 * @author zhaibx
 * @date 2018-9-12
 * @version V1.0
 */
public class WebStartInitListener implements ApplicationListener<ContextRefreshedEvent> {
	
	private Logger log = LogManager.getLogger(WebStartInitListener.class);
	
	@Autowired
	private SystemService systemService;
	
	@Autowired
	protected ScheduleJobService scheduleJobService;

	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		//判断让启动web只执行一次
		if(event.getApplicationContext().getDisplayName().equals("Root WebApplicationContext")) {
			printKeyLoadMessage(event);
		}
	}
	
	/**
	 * 获取Key加载信息
	 */
	public void printKeyLoadMessage(ContextRefreshedEvent event) {
//		注释以下一段，上面判断完启动只执行一次，就不需要此判断了
//		if (Constants.property!=null) {
//			System.out.println("5.1");
//			String isLicence = (String) Constants.property.get(Constants.isLicence);
//			if(StringUtil.isNotEmpty(isLicence)) {
//				return;
//			}
//		}else {
//			System.out.println("5.2");
//			new Constants();
//		}
		StringBuilder sb = new StringBuilder();
		sb.append("\r\n .----------------.  .----------------.  .----------------.  .----------------.  .----------------.  .----------------.  .----------------.  .----------------. \n" +
				"| .--------------. || .--------------. || .--------------. || .--------------. || .--------------. || .--------------. || .--------------. || .--------------. |\n" +
				"| |  _______     | || |     ____     | || |   _____      | || |   _____      | || |  ________    | || |      __      | || |  _________   | || |      __      | |\n" +
				"| | |_   __ \\    | || |   .'    `.   | || |  |_   _|     | || |  |_   _|     | || | |_   ___ `.  | || |     /  \\     | || | |  _   _  |  | || |     /  \\     | |\n" +
				"| |   | |__) |   | || |  /  .--.  \\  | || |    | |       | || |    | |       | || |   | |   `. \\ | || |    / /\\ \\    | || | |_/ | | \\_|  | || |    / /\\ \\    | |\n" +
				"| |   |  __ /    | || |  | |    | |  | || |    | |   _   | || |    | |   _   | || |   | |    | | | || |   / ____ \\   | || |     | |      | || |   / ____ \\   | |\n" +
				"| |  _| |  \\ \\_  | || |  \\  `--'  /  | || |   _| |__/ |  | || |   _| |__/ |  | || |  _| |___.' / | || | _/ /    \\ \\_ | || |    _| |_     | || | _/ /    \\ \\_ | |\n" +
				"| | |____| |___| | || |   `.____.'   | || |  |________|  | || |  |________|  | || | |________.'  | || ||____|  |____|| || |   |_____|    | || ||____|  |____|| |\n" +
				"| |              | || |              | || |              | || |              | || |              | || |              | || |              | || |              | |\n" +
				"| '--------------' || '--------------' || '--------------' || '--------------' || '--------------' || '--------------' || '--------------' || '--------------' |\n" +
				" '----------------'  '----------------'  '----------------'  '----------------'  '----------------'  '----------------'  '----------------'  '----------------' ");
		sb.append("\r\n======================================================================\r\n");
		sb.append("\r\n    欢迎使用"+"本系统"+"\r\n");
		sb.append("\r\n======================================================================\r\n");
		log.info(sb.toString());
		//初始化注册信息
		try {
			//初始化定时任务
			scheduleJobService.initSchedule();
		} catch (Exception e) {
			log.error(e.getMessage(),e);
		}
		//初始化系统配置，在注册信息之后，因为配置信息中有在注册信息中获取的内容
		try {
			systemService.querySysConfigInfo();
		} catch (Exception e) {
			log.error(e.getMessage(),e);
		}
	}
}