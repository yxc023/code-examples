package lambda;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author yangxiaochen
 * @date 2016/12/2 17:17
 */
public class StreamTest {
    public static class Person {
        private String name;
        private int age;
        private int score;
        private char gender;

        public Person(String name, int age, int score, char gender) {
            this.name = name;
            this.age = age;
            this.score = score;
            this.gender = gender;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }

        public int getScore() {
            return score;
        }

        public void setScore(int score) {
            this.score = score;
        }

        public char getGender() {
            return gender;
        }

        public void setGender(char gender) {
            this.gender = gender;
        }

        @Override
        public String toString() {
            return "{" +
                    "'" + name + '\'' +
                    ", " + age +
                    ", " + score +
                    ", " + gender +
                    '}';
        }
    }


    public static void main(String[] args) {
        List<Person> persons = new ArrayList<>();
        persons.add(new Person("yxc", 18, 100, 'm'));
        persons.add(new Person("张三", 40, 44, 'm'));
        persons.add(new Person("丽丽", 17, 94, 'f'));
        persons.add(new Person("李四", 21, 70, 'm'));
        persons.add(new Person("小白", 18, 59, 'f'));
        persons.add(new Person("老王", 50, 77, 'm'));
        persons.add(new Person("TT EE", 28, 60, 'm'));
        persons.add(new Person("Lucy", 24, 50, 'f'));


        List<Person> females = new ArrayList<>();
        for (Person person : persons) {
            if (person.getGender() == 'f') {
                females.add(person);
            }
        }
        females.sort(new Comparator<Person>() {
            @Override
            public int compare(Person p1, Person p2) {
                return Integer.compare(p2.getScore(), p1.getScore());
            }
        });
        StringBuilder builder = new StringBuilder();
        for (Person female : females) {
            builder.append(female.getName());
            builder.append(",");
        }
        System.out.println(builder.toString());

        // 将所有女生名字按照分数从高到低逗号分隔输出
        persons.stream()
                .filter(p -> p.getGender() == 'f')
                .sorted((p1, p2) -> Integer.compare(p2.getScore(), p1.getScore()))
                .map(Person::getName)
                .reduce((name1, name2) -> name1 + "," + name2)
                .ifPresent(result -> System.out.println(result));

//        System.out.println(result);
//        System.out.println();

//        System.out.println(persons);
        Stream<String> names = persons.stream()
                .sorted((p1, p2) -> Integer.compare(p2.getScore(), p1.getScore()))
                .map(Person::getName)
                .peek(System.out::println)
                .flatMap(s -> Stream.of(s.split(" +")));

        System.out.println("stream prepare ok.");
        persons.add(new Person("AA BB", 28, 60, 'm'));
        System.out.println(names.collect(Collectors.toList()));

        // 男女分组
        Map<Character, List<Person>> map = persons.stream()
                .collect(Collectors.groupingBy(p -> p.getGender()));
        System.out.println(map);

        // 男女总分
        Map<Character, Integer> map2 = persons.stream()
                .collect(Collectors.groupingBy(Person::getGender, Collectors.summingInt(Person::getScore))
                );
        System.out.println(map2);

        boolean isAllPass = persons.parallelStream().allMatch(person -> person.getScore() >= 60);


        int sum = persons.parallelStream().peek(System.out::println).mapToInt(Person::getScore).sum();
        System.out.println(sum);

        persons.removeIf(person -> person.getScore() < 60);
        persons.forEach(System.out::println);


        System.out.println(Arrays.asList("huhu", "hha").stream().reduce((s1, s2) -> String.join(",", s1, s2)).get());

        persons.toArray(new Person[persons.size()]);

    }
}
