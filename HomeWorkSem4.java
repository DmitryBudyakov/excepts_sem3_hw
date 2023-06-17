import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.*;

/* Задание
Напишите приложение, которое будет запрашивать у пользователя следующие данные в произвольном порядке,
разделенные пробелом:
ФИО, дата рождения, номер телефона, пол

Форматы данных:
фамилия, имя, отчество - строки
дата рождения - строка формата dd.mm.yyyy
номер телефона - целое беззнаковое число без форматирования
пол - символ латиницей f или m.

1. Приложение должно проверить введенные данные по количеству.
Если количество не совпадает с требуемым, вернуть код ошибки, обработать его и показать пользователю сообщение,
что он ввел меньше или больше данных, чем требуется.
2. Приложение должно попытаться распарсить полученные значения и выделить из них требуемые параметры.
Если форматы данных не совпадают, нужно бросить исключение, соответствующее типу проблемы.
Можно использовать встроенные типы java или создать свои. Исключение должно быть корректно обработано,
пользователю выведено сообщение с информацией, что именно неверно.
3. Если всё введено и обработано верно, должен создаться файл с названием, равным фамилии,
в него в одну строку должны записаться полученные данные, вида:
<Фамилия><Имя><Отчество><дата рождения><номер телефона><пол>
Однофамильцы должны записаться в один и тот же файл, в отдельные строки.
4. Не забудьте закрыть соединение с файлом.
5. При возникновении проблемы с чтением-записью в файл, исключение должно быть корректно обработано,
пользователь должен увидеть стектрейс ошибки.
 */
public class HomeWorkSem4 {
    public static final int USER_DATA_NUMBER = 6;

    public static void main(String[] args) {
        String[] userData = new String[0];
        try {
            userData = getUserDataFromTerminal();
            if (userData != null) {
                // выполнение программы
//                System.out.println(Arrays.toString(userData));
                String filename = userData[0].split(" ")[0];
                File file = new File(filename);
                try {
                    writeDataToFile(file, userData);
                } catch (NullPointerException | IOException e) {
                    e.printStackTrace();
                }
            } else {
                // завершение программы, если есть некорректные данные
                System.out.println("Введены некорректные данные");
                System.out.println("Конец работы программы");
            }
        } catch (UserFullNameDataException | UserBirthdayDataException | UserPhoneDataException
                | UserSexDataException e) {
            e.printStackTrace();
            
        }

    }

    /**
     * Запись в файл введенных данных
     * 
     * @param file     имя файла
     * @param userData Массив данных пользователя String[]
     */
    public static void writeDataToFile(File file, String[] userData) throws NullPointerException, IOException {
        // file = null; // для проверки на NullPointer
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < userData.length; i++) {
            if (i != userData.length - 1) {
                stringBuilder.append("<" + userData[i] + ">");
            } else {
                stringBuilder.append("<" + userData[i] + ">");
                stringBuilder.append("\n");
            }
        }

        try (FileWriter writer = new FileWriter(file, true)) {
            writer.write(stringBuilder.toString());
            System.out.println("Запись в файл: OK\n");
            System.out.println("Имя файла: " + file);
            System.out.println("Записанные данные: " + stringBuilder.toString());
        } catch (IOException e) {
            throw new IOException("Проблемы с записью в файл");
        } catch (NullPointerException e) {
            throw new NullPointerException("Проблемы с записью в файл");
        }
    }

    /**
     * Получение данных от пользователя
     * 
     * @return Возвращает массив данных String[]
     */
    public static String[] getUserDataFromTerminal()
            throws UserFullNameDataException, UserBirthdayDataException, UserPhoneDataException, UserSexDataException {
        Scanner scanner = new Scanner(System.in, StandardCharsets.UTF_8);
        String[] data = new String[USER_DATA_NUMBER];
        String prompt1 = "Введите данные о пользователе через пробел";
        String prompt2 = "[ФИО, дата рождения(dd.mm.yy), номер телефона(89161234567), пол(f,m)]";
        String prompt3 = "Пример ввода: Иванов Иван Иванович 01.01.1980 891612345678 m";
        String prompt4 = "Введите данные: ";
        String promptError = "Ввод пустой строки недопустим.";

        System.out.println(prompt1 + " " + prompt2);
        System.out.println(prompt3);
        System.out.print(prompt4);

        String line = scanner.nextLine();

        // проверка на пустую строку
        while (line.isEmpty()) {
            System.out.print(promptError + " " + prompt4);
            line = scanner.nextLine();
        }
        scanner.close();

        List<String> tmp = new ArrayList<>();
        tmp = Arrays.asList(line.split(" "));

        // проверка на количество введенных данных
        int code = checkCompleteData(tmp);

        switch (code) {
            case -1:
                System.out.println(code + ": " + "Введено меньшее количество данных, чем требовалось.");
                return null;
            case -2:
                System.out.println(code + ": " + "Введено большее количество данных, чем требовалось.");
                return null;
            default:
                System.out.println(code + ": " + "Количество данных - ОК");
        }

        // Компановка данных, если количество данных верное
        // ФИО - только буквы
        String[] userFullName = new String[3];
        for (int i = 0; i < 3; i++) {
            userFullName[i] = tmp.get(i);
        }

        // проверка ФИО на валидность
        try {
            checkUserFullNameData(userFullName);
        } catch (UserFullNameDataException e) {
            e.printStackTrace();
            System.out.println(e.getInvalidData());
            System.out.println(e.getDetailInfo());
            throw new UserFullNameDataException();
        }

        // День рождения (dd.mm.yy) - цифры с точками
        String userBirthday = tmp.get(3);
        try {
            checkUserBirthdayData(userBirthday);
        } catch (UserBirthdayDataException e) {
            e.printStackTrace();
            System.out.println(e.getInvalidData());
            System.out.println(e.getDetailInfo());
            throw new UserBirthdayDataException();
        }

        // Номер телефона (89161234567) - только цифры
        String userPhone = tmp.get(4);
        try {
            checkUserPhoneData(userPhone);
        } catch (UserPhoneDataException e) {
            e.printStackTrace();
            System.out.println(e.getInvalidData());
            System.out.println(e.getDetailInfo());
            throw new UserPhoneDataException();
        }

        // Пол - f или m
        String userSex = tmp.get(5);
        try {
            checkUserSex(userSex);
        } catch (UserSexDataException e) {
            e.printStackTrace();
            System.out.println(e.getInvalidData());
            System.out.println(e.getDetailInfo());
            throw new UserSexDataException();
        }

        // итоговый масссив после проверки всех данных
        for (int i = 0; i < tmp.size(); i++) {
            data[i] = tmp.get(i);
        }

        return data;
    }

    /**
     * Проверяет количество введенных данных, выдаёт код ошибки
     * 
     * @param list Проверяемый список данных
     * @return Возвращаемый код ошибки (-1 - меньше, чем надо, -2 - больше, 0 -
     *         сколько надо)
     */
    private static int checkCompleteData(List<String> list) {
        int completeDataSize = USER_DATA_NUMBER; // кол-во блоков данных, сколько должно быть
        int actualDataSize = list.size(); // актуальное количество
        if (actualDataSize < completeDataSize)
            return -1; // данных меньше
        else if (actualDataSize > completeDataSize)
            return -2; // данных больше
        return 0;
    }

    /**
     * Проверяем, что в ФИО только буквы
     * 
     * @param userName На входе ФИО в виде String[]
     */
    private static void checkUserFullNameData(String[] userName) throws UserFullNameDataException {
        UserFullNameDataException exception = new UserFullNameDataException();
        String reason = "Причина: ";
        for (String s : userName) {
            char[] chars = s.toCharArray();
            // for (char ch : chars) {
            for (int i = 0; i < chars.length; i++) {
                if (!Character.isLetter(chars[i])) {
                    System.out.println("Проверка ФИО: NG");
                    exception.setInvalidData("Косяк в слове: " + s + ", позиция:" + (i+1));
                    exception.setDetailInfo(reason + "ФИО должно состоять только из букв");
                    throw exception;
                }
            }
        }
        System.out.println("Проверка ФИО: OK");
    }

    /**
     * Проверка формата даты дня рождения
     * 
     * @param birthday
     * @throws UserBirthdayDataException
     */
    private static void checkUserBirthdayData(String birthday) throws UserBirthdayDataException {
        UserBirthdayDataException exception = new UserBirthdayDataException();
        String reason = "Причина: ";
        if (!birthday.matches("[0-3][0-9]\\.[01][0-9]\\.[12][09][0-9][0-9]")) {
            System.out.println("Проверка даты рождения: NG");
            exception.setInvalidData("Косяк в формате даты рождения: " + birthday);
            exception.setDetailInfo(reason + "Дата должна иметь формат dd.mm.yyyy, например: 01.01.2001");
            throw exception;
        }
        System.out.println("Проверка даты рождения: OK");
    }

    /**
     * Проверка формата номера телефона
     * 
     * @param phoneNumber
     * @throws UserPhoneDataException
     */
    private static void checkUserPhoneData(String phoneNumber) throws UserPhoneDataException {
        UserPhoneDataException exception = new UserPhoneDataException();
        String reason = "Причина: ";
        if (!phoneNumber.matches("[0-9]+")) {
            System.out.println("Проверка номера телефона: NG");
            exception.setInvalidData("Косяк в номере телефона: " + phoneNumber);
            exception.setDetailInfo(reason + "Номер телефона должен состоять только из цифр, например: 89161234567");
            throw exception;
        }
        System.out.println("Проверка номера телефона: OK");
    }

    /**
     * Проверка правильности ввода пола
     * 
     * @param sex
     * @throws UserSexDataException
     */
    private static void checkUserSex(String sex) throws UserSexDataException {
        UserSexDataException exception = new UserSexDataException();
        String reason = "Причина: ";
        char[] sexChars = sex.toCharArray();
        if (sexChars.length > 1) {
            // System.out.println("Пол должен иметь один символ f или m");
            System.out.println("Проверка пола: NG");
            exception.setInvalidData("Косяк в формате пола: " + sex);
            exception.setDetailInfo(reason + "Пол должен иметь только один символ - f или m");
            throw exception;
        } else {
            if (!(sexChars[0] == 'f' || sexChars[0] == 'm')) {
                // System.out.println("Косяк в формате пола. Пол должен иметь символ f или m");
                System.out.println("Проверка пола: NG");
                exception.setInvalidData("Косяк в формате пола: " + sex);
                exception.setDetailInfo(reason + "Пол должен иметь символ f или m");
                throw exception;
            }
        }
        System.out.println("Проверка пола: OK");
    }
}
