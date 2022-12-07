package courses.dev.java.jdbc.assignments;

public class JdbcAssignment {


    public static void main(String[] args) {
        UserRepository repo = new PostgressUserRepository();
        System.out.println("Creating users:");
        for (int i = 0; i < 5; i++) {
            repo.createUser(new User(i, "bob" + i, i));
        }
        repo.getAllUsers().forEach(System.out::println);
        System.out.println("\n editing one:");
        User toUpdate = repo.getAllUsers().get(3);


        repo.updateUserById(toUpdate.getId(), new User("Alice", 34));
        repo.getAllUsers().forEach(System.out::println);
        System.out.println("\n deleting all");
        for (User user :
                repo.getAllUsers()) {
            repo.deleteUserById(user.getId());
        }
        repo.getAllUsers().forEach(System.out::println);
        System.out.println("all deleted");
    }
}
