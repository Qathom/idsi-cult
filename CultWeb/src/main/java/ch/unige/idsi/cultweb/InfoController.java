package ch.unige.idsi.cultweb;

import java.io.IOException;

import ch.unige.idsi.cultweb.api.DataAccessObject;
import ch.unige.idsi.cultweb.model.Place;
import ch.unige.idsi.cultweb.model.Place.Infrastructure;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/info")
public class InfoController {

	private DataAccessObject dao;

	public InfoController() throws IOException {
		dao = new DataAccessObject();
	}

	@RequestMapping(value = "/{infrastructure}/{id:[\\d]+}", method = RequestMethod.GET)
	public String returnPlaceView(ModelMap model,
			@PathVariable String infrastructure, @PathVariable int id)
			throws IOException {

		try {
			Infrastructure inf = Infrastructure.valueOf(infrastructure.toUpperCase());
			if (inf != null) {
				Place place = dao.getPlace(id, inf);
				if (place != null) {
					model.addAttribute("museum", place);
					return "info";
				}
			}
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		}

		return "404";
	}
}