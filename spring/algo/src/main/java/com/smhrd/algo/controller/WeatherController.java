import com.smhrd.algo.model.entity.WeatherData;
import com.smhrd.algo.repository.WeatherDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/weather")
public class WeatherController {

    @Autowired
    private WeatherDataRepository weatherDataRepository;

    @GetMapping("/fetch")
    @ResponseBody
    public List<WeatherData> fetchData() {
        // Fetch data from the database
        return weatherDataRepository.findAll();
    }

    @GetMapping("/show")
    public String showWeather(Model model) {
        // Fetch data and pass it to the Thymeleaf template
        List<WeatherData> weatherDataList = weatherDataRepository.findAll();
        model.addAttribute("weatherDataList", weatherDataList);
        return "weather";
    }
}