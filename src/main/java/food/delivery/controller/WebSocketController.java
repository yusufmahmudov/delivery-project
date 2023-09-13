package food.delivery.controller;

import food.delivery.component.WebSocketUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/orders")
@RequiredArgsConstructor
public class WebSocketController {

    private final WebSocketUtil webSocketUtil;


    @GetMapping("/new")
    public String index(Model model) {
        return "index";
    }


}
