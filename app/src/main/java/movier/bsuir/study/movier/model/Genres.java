package movier.bsuir.study.movier.model;

import java.util.List;

public class Genres {
    private static final String action = "Боевик";
    private static final String adventure = "Приключения";
    private static final String cartoon = "Мультфильм";
    private static final String comedy = "Комедия";
    private static final String criminal = "Криминал";
    private static final String documentary = "Документальный";
    private static final String drama = "Драма";
    private static final String family = "Семейный";
    private static final String fantasy = "Фентези";
    private static final String history = "История";
    private static final String horror = "Ужасы";
    private static final String music = "Музыка";
    private static final String detective = "Детектив";
    private static final String melodramm = "Мелодрамма";
    private static final String fantastic = "Фантастика";
    private static final String tv_movie = "Телевизионный фильм";
    private static final String thriller = "Триллер";
    private static final String military = "Военный";
    private static final String western = "Вестерн";


    public static String getGenre(List<Integer> genreIdList) {
        String result = "";

        for (Integer genreId : genreIdList) {
            switch (genreId) {
                case 28:
                    result = result + action + " ";
                    break;
                case 12:
                    result = result + adventure + " ";
                    break;
                case 16:
                    result = result + cartoon + " ";
                    break;
                case 35:
                    result = result + comedy + " ";
                    break;
                case 80:
                    result = result + criminal + " ";
                    break;
                case 99:
                    result = result + documentary + " ";
                    break;
                case 18:
                    result = result + drama + " ";
                    break;
                case 10751:
                    result = result + family + " ";
                    break;
                case 14:
                    result = result + fantasy + " ";
                    break;
                case 36:
                    result = result + history + " ";
                    break;
                case 27:
                    result = result + horror + " ";
                    break;
                case 10402:
                    result = result + music + " ";
                    break;
                case 9648:
                    result = result + detective + " ";
                    break;
                case 10749:
                    result = result + melodramm + " ";
                    break;
                case 878:
                    result = result + fantastic + " ";
                    break;
                case 10770:
                    result = result + tv_movie + " ";
                    break;
                case 53:
                    result = result + thriller + " ";
                    break;
                case 10752:
                    result = result + military + " ";
                    break;
                case 37:
                    result = result + western + " ";
                    break;
                default:
                    result = result + "Неопределён" + " ";
                    break;
            }
        }
        return result;
    }
}
