package ru.fortushin.tvc.util;

import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class UserNameToLoginConverter {

    public String mapUserNameToLogin(String userName){
        String[] nameParts = userName.split(" ");
        String lastName = mapRussianToEnglish(nameParts[0]);
        String firstName = mapRussianToEnglish(nameParts[1]);

        return firstName + "_" + lastName;
    }

    public static String mapRussianToEnglish(String text) {
        String nameToLowerCase = text.toLowerCase();
        String[] russianLowerCase = {"а", "б", "в", "г", "д", "е", "ё", "ж", "з", "и", "й", "к", "л", "м", "н", "о", "п", "р", "с", "т", "у", "ф", "х", "ц", "ч", "ш", "щ", "ъ", "ы", "ь", "э", "ю", "я"};
        String[] englishRussianLikeLowerCase = {"a", "b", "v", "g", "d", "e", "e", "j", "z", "i","i", "k", "l", "m", "n", "o", "p", "r", "s", "t", "u", "f", "h", "ts", "ch", "sh", "sch", "'", "y", "", "e", "yu", "ya"};

        StringBuilder result = new StringBuilder();
        for (int i = 0; i < text.length(); i++) {
            String c = String.valueOf(nameToLowerCase.charAt(i));
            int index = Arrays.binarySearch(russianLowerCase, c);
            if (index != -1) {
                result.append(englishRussianLikeLowerCase[index]);
            } else {
                result.append(c);
            }
        }
        return result.toString();

    }


}
