/**
 * Class User
 * 
 * @author Yipeng Zhang & Zhanqian Wu
 */

package roles;

import files.FileInfoReader;

/**
 * Represent a user
 */

public abstract class User {

    String id; // user id
    String name; // name of user
    String username; // username
    String password; //  password
    
    protected abstract boolean login(String username, String password, FileInfoReader file);

    // get user information after login in
    protected abstract User getLogin(String username, String password, FileInfoReader file);
   
    public User Login(String username, String password, FileInfoReader file) {
        if (login(username, password, file)) {
            return getLogin(username, password, file);
        }
        return null;
    }
    
    public String getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public String getUsername() {
        return this.username;
    }
}