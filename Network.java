/** Represents a social network. The network has users, who follow other uesrs.
 *  Each user is an instance of the User class. */
public class Network {

    // Fields
    private User[] users;  // the users in this network (an array of User objects)
    private int userCount; // actual number of users in this network

    /** Creates a network with a given maximum number of users. */
    public Network(int maxUserCount) {
        this.users = new User[maxUserCount];
        this.userCount = 0;
    }

    /** Creates a network  with some users. The only purpose of this constructor is 
     *  to allow testing the toString and getUser methods, before implementing other methods. */
    public Network(int maxUserCount, boolean gettingStarted) {
        this(maxUserCount);
        users[0] = new User("Foo");
        users[1] = new User("Bar");
        users[2] = new User("Baz");
        userCount = 3;
    }

    public int getUserCount() {
        return this.userCount;
    }

    /** Finds in this network, and returns, the user that has the given name.
     *  If there is no such user, returns null.
     *  Notice that the method receives a String, and returns a User object. */
    public User getUser(String name) {
        for (int i = 0; i < userCount; i++){
            if (this.users[i].getName().equals(name)){
                return this.users[i];
            }
        }
        return null;
    }

    /** Adds a new user with the given name to this network.
    *  If ths network is full, does nothing and returns false;
    *  If the given name is already a user in this network, does nothing and returns false;
    *  Otherwise, creates a new user with the given name, adds the user to this network, and returns true. */
    public boolean addUser(String name) {
        // check if network is full.
        if (userCount == users.length){
            return false;
        }
        // check if user is already in the network.
        if (getUser(name) != null){
            return false;
        }
        // else add the usesr to the netwrokand add 1 to usercount.
        users[userCount] = new User(name);
        userCount++;
        return true;
    }

    /** Makes the user with name1 follow the user with name2. If successful, returns true.
     *  If any of the two names is not a user in this network,
     *  or if the "follows" addition failed for some reason, returns false. */
    public boolean addFollowee(String name1, String name2) {
        User u1 = getUser(name1);
        User u2 = getUser(name2);
        
        // check if both users are in the network.
        if (u1 == null || u2 == null){
            return false;
        }
        // make name1 follow name2.
        return u1.addFollowee(name2);
    }
    
    /** For the user with the given name, recommends another user to follow. The recommended user is
     *  the user that has the maximal mutual number of followees as the user with the given name. */
    public String recommendWhoToFollow(String name) {
        User user = getUser(name);
        if (user == null){
            return null;
        }
        int count = -1;
        String recommend = null;
        // go over all the users in the network.
        for (int i = 0; i < userCount; i++){
            User canidate = users[i];
            // check if user doesn't already follows name and that we don't check on user itself.
            if (!user.follows(canidate.getName()) && user != canidate){
                // check if mutual cout is greater then privious count. if so update count and recommendation.
                int mutual = user.countMutual(canidate);
                if (count < mutual){
                    count = mutual;
                    recommend = canidate.getName();
                }
            }
        }
        return recommend;
    }

    /** Computes and returns the name of the most popular user in this network: 
     *  The user who appears the most in the follow lists of all the users. */
    public String mostPopularUser() {
        int bestcount = -1;
        String mostpopular = null;
        
        // go over all the usesrs.
        for (int i = 0; i < userCount; i++){
            String canidate = users[i].getName();
            int count = 0;

            // count how many follow canidate.
            for (int j = 0; j < userCount; j++){
                if(users[j].follows(canidate)){
                    count++;
                }
            }
            // if current canidate is more popular update.
            if (count > bestcount){
                bestcount = count;
                mostpopular = canidate;
            }
           
        }
        return mostpopular;
    }

    /** Returns the number of times that the given name appears in the follows lists of all
     *  the users in this network. Note: A name can appear 0 or 1 times in each list. */
    private int followeeCount(String name) {
        int count = 0;
        // go over all the users in the network.
        for (int i = 0; i < userCount; i++){
            // check if the given name apears int the users follos list. If it does add +1 to count.
            if (users[i].follows(name)){
                count++;
            }
        }
        return count;
    }

    // Returns a textual description of all the users in this network, and who they follow.
    public String toString() {
        // create the netwrok string.
        String strnetwork = "Network:";
        if (userCount > 0){
             strnetwork = strnetwork + "\n";
        }
        // go over all the users in the network.
        for (int i = 0; i < userCount; i++){
            User currntUser = users[i];
            // add the user name to final string.
            // if its the las name dont add row.
            if (userCount - i == 1){
                strnetwork = strnetwork + currntUser.toString();
            }
            else {
                strnetwork = strnetwork + currntUser.toString() + "\n";
            }
        }

       return strnetwork;
    }
}
