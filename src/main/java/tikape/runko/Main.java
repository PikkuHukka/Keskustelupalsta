package tikape.runko;

import java.util.HashMap;
import java.util.List;
import java.util.Random;
import spark.ModelAndView;
import static spark.Spark.*;
import spark.template.thymeleaf.ThymeleafTemplateEngine;
import tikape.runko.database.AlueDao;
import tikape.runko.database.Database;
import tikape.runko.database.OpiskelijaDao;
import tikape.runko.database.VastausDao;
import tikape.runko.domain.Alue;

public class Main {

    public static void main(String[] args) throws Exception {
        Database database = new Database("jdbc:sqlite:keskustelupalsta.db");
        database.init();

        OpiskelijaDao opiskelijaDao = new OpiskelijaDao(database);
        VastausDao vastausDao = new VastausDao(database);
        AlueDao alueDao = new AlueDao(database);

        get("/", (req, res) -> {
            HashMap map = new HashMap<>();

            List<Alue> alueet = alueDao.findAll();

            for (Alue alue : alueet) {
                alue.setLukumaara(alueDao.viestienLukumaara(alue.getAlue_id()) + alueDao.onkoAvausta(alue.getAlue_id()));
                alue.setViimeisin(alueDao.viimeisinViesti(alue.getAlue_id()));
            }

            map.put("alueet", alueet);

            return new ModelAndView(map, "index");
        }, new ThymeleafTemplateEngine());

        post("/", (req, res) -> {
            String nimi = req.queryParams("nimi");
            Random random = new Random();
            Alue a = new Alue(Math.abs(nimi.hashCode()+random.nextInt(100)), nimi);
            alueDao.lisaaAlue(a);
            res.redirect("/");
            return "";
        });

        get("/keskustelu/:id", (req, res) -> {
            HashMap map = new HashMap<>();
//          map.put("avaus", avausDao.findOne(Integer.parseInt(req.params("id")))));
//          map.put("alue", avausDao.findAlue(Integer.parseInt(req.params("id"))))));
            map.put("vastaus", vastausDao.findAll());
            return new ModelAndView(map, "opiskelijat");
        }, new ThymeleafTemplateEngine());

        get("/opiskelijat/:id", (req, res) -> {
            HashMap map = new HashMap<>();
            map.put("opiskelija", opiskelijaDao.findOne(Integer.parseInt(req.params("id"))));

            return new ModelAndView(map, "opiskelija");
        }, new ThymeleafTemplateEngine());
    }
}
