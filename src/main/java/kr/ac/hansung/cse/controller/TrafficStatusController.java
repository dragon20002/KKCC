package kr.ac.hansung.cse.controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.ac.hansung.cse.service.SectionService;

@Controller
public class TrafficStatusController {
	private static final String FILE_DIR = "/STLC/resources/files";
	private static final String[] EWSN = { "/east", "/west", "/south", "/north" }; 
	private static final String IMAGE_DIR = "/STLC/resources/images";
	private static final String[] LIGHT = { "/light-green", "/light-left", "/light-yellow", "/light-red" };

	@Autowired
	private MessageController messageController;
	
	@Autowired
	private SectionService sectionService;
	
	@RequestMapping(value = "/trafficstatus/{id}")
	public String traffic(Model model, @PathVariable("id") int id) {
		model.addAttribute("id", id);
		return "trafficstatus";
	}

	@SuppressWarnings("deprecation")
	@RequestMapping(value = "/ajaxtrafficstatus.do/{id}")
	public @ResponseBody Map<String, List<Map<String, String>>> getBoardList(
			Locale locale, Model model, HttpServletRequest request, @PathVariable("id") int id) {
		Map<String, List<Map<String, String>>> result = new HashMap<>();
		List<Map<String, String>> list = new ArrayList<>(4);
		
		// 사고상황 인지
		File file = new File(request.getServletContext().getRealPath("/resources/files") + "/" + id + "/global.txt");
		if (file.exists()) {
			String text;
			try {
				FileReader in = new FileReader(file);
				BufferedReader reader = new BufferedReader(in);
				text = reader.readLine();
				reader.close();
				
				String[] textDatas = text.split(" ");
				if (textDatas[0].equals("1")) {
					String sectionName = (id != 0) ? new String(sectionService.getSectionById(id).getName()) : "한성대 사거리";
					messageController.sendMessage(sectionName + " 사고 발생");
				}

				/* map에 저장 */
				Map<String, String> map = new HashMap<>();
				list.add(map);
				map.put("remaintime", textDatas[1]);
				map.put("totaltime", textDatas[2]);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		for (int i = 0; i < 4; i++) {
			Map<String, String> map = new HashMap<>();
			list.add(map);

			/* text 파일 */
			file = new File(request.getRealPath("/resources/files") + "/" + id + EWSN[i] + ".txt");
			if (!file.exists())
				continue;
			
			// 실시간 이미지
			String text;
			try {
				FileReader in = new FileReader(file);
				BufferedReader reader = new BufferedReader(in);
				text = reader.readLine();
				reader.close();
			} catch (IOException e) {
				continue;
			}
			String[] textDatas = text.split(" ");
			String imgPath = FILE_DIR + "/" + id + EWSN[i] + textDatas[0] + "_result.jpg"; //이미지 경로 찾기

			file = new File(request.getServletContext().getRealPath("/resources/files") + "/" + id + EWSN[i] + textDatas[0] + "_result.jpg");
			if (!file.exists())
				continue;

			// 분석 결과
			int time = Integer.parseInt(textDatas[0]);
			int hour = time / 3600 % 24 + 9;
			int min = (time % 3600) / 60;
			int sec = time % 60;
			String timeLabel = String.format("%d시 %d분 %d초", hour, min, sec); 
			String dirLabel = String.format("정면: %s대 후면: %s대 측면: %s대", textDatas[1], textDatas[2], textDatas[3]);

			// 신호등 상태
			String light = "";
			for (int j = 4; j < 8; j++) {
				if (textDatas[j].equals("1")) {
					light = IMAGE_DIR + LIGHT[j - 4] + ".png";
					break;
				}
			}

			/* map에 저장 */
			map.put("imgPath", imgPath);
			map.put("timeLabel", timeLabel);
			map.put("dirLabel", dirLabel);
			map.put("light", light);
		}

		result.put("items", list);
		
//		System.out.println(result.get("items").get(0).get("remaintime"));
//		System.out.println(result.get("items").get(0).get("totaltime"));
//		for (int i = 0; i < 4; i++) {
//			System.out.println(result.get("items").get(i+1).get("imgPath"));
//			System.out.println(result.get("items").get(i+1).get("timeLabel"));
//			System.out.println(result.get("items").get(i+1).get("dirLabel"));
//			System.out.println(result.get("items").get(i+1).get("light"));
//		}

		return result;
	}

}