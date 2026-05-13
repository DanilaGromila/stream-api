package by.clevertec;

import by.clevertec.model.*;
import by.clevertec.util.Util;

import java.time.LocalDate;
import java.time.Period;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {

    public static void main(String[] args) {
//        task1();
//        task2();
//        task3();
//        task4();
//        task5();
//        task6();
//        task7();
//        task8();
//        task9();
//        task10();
//        task11();
//        task12();
//        task13();
//        task14();
//        task15();
//        task16();
//        task17();
//        task19();
//        task20();
//        task21();
//        task22();
    }

    public static void task1() {
        int size = 7;
        List<Animal> animals = Util.getAnimals();
        List<Animal> filtered = animals.stream().filter(animal -> animal.getAge() >= 10 && animal.getAge() <= 20)
                .sorted(Comparator.comparingInt(Animal::getAge))
                .toList();

        List<List<Animal>> zoos = new ArrayList<>();
        for (int i = 0; i < filtered.size(); i += size) {
            int end = Math.min(i + size, filtered.size());
            zoos.add(filtered.subList(i, end));
        }
        zoos.get(2).forEach(System.out::println);
    }

    public static void task2() {
        List<Animal> animals = Util.getAnimals();
        List<Animal> japaneseAnimals = animals.stream()
                .filter(animal -> "Japanese".equals(animal.getOrigin()))
                .peek(animal -> animal.setBread(animal.getBread().toUpperCase()))
                .toList();

        japaneseAnimals.stream()
                .filter(animal -> "Female".equals(animal.getGender()))
                .map(Animal::getBread)
                .forEach(System.out::println);
    }

    public static void task3() {
        List<Animal> animals = Util.getAnimals();
        animals.stream().filter(animal -> animal.getAge() > 30)
                .map(Animal::getOrigin)
                .filter(origin -> origin.charAt(0) == 'A')
                .distinct()
                .forEach(System.out::println);
    }

    public static void task4() {
        List<Animal> animals = Util.getAnimals();
        long count = animals.stream().filter(animal -> "Female".equals(animal.getGender())).count();
        System.out.println(count);
    }

    public static void task5() {
        List<Animal> animals = Util.getAnimals();
        System.out.println(animals.stream()
                .filter(animal -> animal.getAge() >= 20 && animal.getAge() <= 30)
                .anyMatch(animal -> "Hungarian".equals(animal.getOrigin())));
    }

    public static void task6() {
        List<Animal> animals = Util.getAnimals();
        System.out.println(animals.stream().allMatch(animal -> "Female".equals(animal.getGender()) || "Male".equals(animal.getGender())));
        animals.stream().filter(animal -> !"Female".equals(animal.getGender()) && !"Male".equals(animal.getGender())).forEach(System.out::println);
    }

    public static void task7() {
        List<Animal> animals = Util.getAnimals();
        System.out.println(animals.stream().noneMatch(animal -> "Oceania".equals(animal.getOrigin())));
    }

    public static void task8() {
        List<Animal> animals = Util.getAnimals();
        System.out.println(animals.stream().sorted(Comparator.comparing(Animal::getBread)).limit(100).max(Comparator.comparingInt(Animal::getAge)));
    }

    public static void task9() {
        List<Animal> animals = Util.getAnimals();
        System.out.println(animals.stream().map(Animal::getBread).map(String::toCharArray).mapToInt(chars -> chars.length).min().orElse(0));
    }

    public static void task10() {
        List<Animal> animals = Util.getAnimals();
        System.out.println(animals.stream().mapToInt(Animal::getAge).reduce(0, Integer::sum));
    }

    public static void task11() {
        List<Animal> animals = Util.getAnimals();
        System.out.println(animals.stream().filter(animal -> "Indonesian".equals(animal.getOrigin())).mapToInt(Animal::getAge).average().orElse(0));

    }

    public static void task12() {
        List<Person> persons = Util.getPersons();
        persons.stream().filter(person -> "Male".equals(person.getGender()))
                .filter(person -> Period.between(person.getDateOfBirth(), LocalDate.now()).getYears() >= 18 && Period.between(person.getDateOfBirth(), LocalDate.now()).getYears() <= 30)
                .sorted(Comparator.comparingInt(Person::getRecruitmentGroup)).limit(200).forEach(System.out::println);

    }

    public static void task13() {
        List<House> houses = Util.getHouses();
        List<Integer> evacuated = new ArrayList<>();
        List<Person> firstStage = houses.stream().filter(house -> "Hospital".equals(house.getBuildingType()))
                .flatMap(house -> house.getPersonList().stream())
                .limit(500)
                .peek(person -> evacuated.add(person.getId()))
                .toList();

        List<Person> secondStage = houses.stream().filter(house -> !"Hospital".equals(house.getBuildingType()))
                .flatMap(house -> house.getPersonList().stream())
                .filter(person -> Period.between(person.getDateOfBirth(), LocalDate.now()).getYears() <= 18 || Period.between(person.getDateOfBirth(), LocalDate.now()).getYears() >= 60)
                .filter(person -> !evacuated.contains(person.getId()))
                .limit(500 - firstStage.size())
                .peek(person -> evacuated.add(person.getId()))
                .toList();

        List<Person> thirdStage = houses.stream().filter(house -> !"Hospital".equals(house.getBuildingType()))
                .flatMap(house -> house.getPersonList().stream())
                .filter(person -> !evacuated.contains(person.getId()))
                .limit(500 - firstStage.size() - secondStage.size())
                .peek(person -> evacuated.add(person.getId()))
                .toList();

        Stream.concat(firstStage.stream(), Stream.concat(secondStage.stream(), thirdStage.stream())).forEach(System.out::println);
    }

    public static void task14() {
        List<Car> cars = Util.getCars();
        List<Car> remaining = new ArrayList<>(cars);

        List<Car> turkmenistan = cars.stream().filter(car -> "Jaguar".equals(car.getCarMake()) || "White".equals(car.getColor())).toList();
        remaining.removeAll(turkmenistan);

        List<Car> uzbekistan = remaining.stream().filter(car -> car.getMass() <= 1500 && List.of("BMW", "Lexus", "Chrysler", "Toyota").contains(car.getCarMake())).toList();
        remaining.removeAll(uzbekistan);

        List<Car> kazahstan = remaining.stream().filter(car -> car.getMass() > 4000 && "Black".equals(car.getColor()) || List.of("GMC", "Dodge").contains(car.getCarMake())).toList();
        remaining.removeAll(kazahstan);

        List<Car> kirgiztan = remaining.stream().filter(car -> car.getReleaseYear() < 1982 || List.of("Civic", "Cherokee").contains(car.getCarModel())).toList();
        remaining.removeAll(kirgiztan);

        List<Car> russia = remaining.stream().filter(car -> !List.of("Yellow", "Green", "Blue", "Red").contains(car.getColor()) || car.getPrice() > 40000).toList();
        remaining.removeAll(russia);

        List<Car> mongolia = remaining.stream().filter(car -> car.getVin().contains("59")).toList();
        remaining.removeAll(mongolia);

        double costTurkmenistan = turkmenistan.stream().mapToInt(Car::getMass).sum() / 1000.0 * 7.14;
        System.out.println("Turkmenistan " + costTurkmenistan);

        double costUzbekistan = uzbekistan.stream().mapToInt(Car::getMass).sum() / 1000.0 * 7.14;
        System.out.println("Uzbekistan " + costUzbekistan);

        double costKazahstan = kazahstan.stream().mapToInt(Car::getMass).sum() / 1000.0 * 7.14;
        System.out.println("Kazahstan " + costKazahstan);

        double costKirgiztan = kirgiztan.stream().mapToInt(Car::getMass).sum() / 1000.0 * 7.14;
        System.out.println("Kirgiztan " + costKirgiztan);

        double costRussia = russia.stream().mapToInt(Car::getMass).sum() / 1000.0 * 7.14;
        System.out.println("Russia " + costRussia);

        double costMongolia = mongolia.stream().mapToInt(Car::getMass).sum() / 1000.0 * 7.14;
        System.out.println("Mongolia " + costMongolia);
    }

    public static void task15() {
        List<Flower> flowers = Util.getFlowers();
        List<Flower> sortedFlowers = flowers.stream()
                .sorted(Comparator.comparing(Flower::getOrigin).reversed()
                        .thenComparing(Comparator.comparing(Flower::getPrice).reversed())
                        .thenComparing(Comparator.comparing(Flower::getWaterConsumptionPerDay).reversed()))
                .filter(flower -> List.of("S", "T", "U", "V", "W", "X", "Y", "Z", "A", "B", "C").contains(String.valueOf(flower.getCommonName().charAt(0))))
                .filter(flower -> flower.getFlowerVaseMaterial().stream().anyMatch(material -> List.of("Aluminium", "Steel", "Glass").contains(material))).toList();

        List<Double> flowersPrice = sortedFlowers.stream().map(flower -> flower.getPrice() + flower.getWaterConsumptionPerDay() * 365 * 5 * 1.39).toList();
        Double totalPrice = flowersPrice.stream().mapToDouble(Double::doubleValue).sum();
    }

    public static void task16() {
        List<Student> students = Util.getStudents();
        students.stream().filter(student -> student.getAge() <= 18).sorted(Comparator.comparing(Student::getSurname))
                .forEach(student -> System.out.println(student.getSurname() + " " + student.getAge()));
    }

    public static void task17() {
        List<Student> students = Util.getStudents();
        students.stream().map(Student::getGroup).distinct().forEach(System.out::println);
    }

    public static void task18() {
        List<Student> students = Util.getStudents();
        Map<String, Double> avgAge = students.stream().collect(Collectors.groupingBy(Student::getFaculty, Collectors.averagingInt(Student::getAge)));
        avgAge.entrySet().stream()
                .sorted(Map.Entry.<String, Double>comparingByValue().reversed())
                .forEach(entry -> System.out.println(entry.getKey() + " " + entry.getValue()));

    }

    public static void task19() {
        List<Student> students = Util.getStudents();
        List<Examination> examinations = Util.getExaminations();
        Scanner scanner = new Scanner(System.in);
        System.out.print("Введите группу: ");
        String group = scanner.next();
        examinations.stream().filter(examination -> examination.getExam3() >= 3)
                .map(examination -> students.get(examination.getStudentId()))
                .filter(student -> group.equals(student.getGroup()))
                .forEach(System.out::println);
    }

    public static void task20() {
        List<Student> students = Util.getStudents();
        List<Examination> examinations = Util.getExaminations();

        Map<Integer, Integer> examMap = examinations.stream()
                .collect(Collectors.toMap(Examination::getStudentId, Examination::getExam1));

        students.stream()
                .collect(Collectors.groupingBy(Student::getFaculty,
                        Collectors.averagingInt(student -> examMap.getOrDefault(student.getId(), 0))))
                .entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .ifPresent(entry -> System.out.println(entry.getKey() + " " + entry.getValue()));
    }

    public static void task21() {
        List<Student> students = Util.getStudents();
        System.out.println(students.stream().collect(Collectors.groupingBy(Student::getGroup, Collectors.counting())));
    }

    public static void task22() {
        List<Student> students = Util.getStudents();
        Map<String, Optional<Student>> map = students.stream()
                .collect(Collectors.groupingBy(Student::getFaculty, Collectors.minBy(Comparator.comparingInt(Student::getAge))));

        map.forEach((faculty, student) -> student.ifPresent(s -> System.out.println(faculty + ":" + s.getAge())));
    }
}