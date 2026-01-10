package com.project.foodmarket.food_management.configuration;

public class MongoContextHolder {
    private static final ThreadLocal<String> context = new ThreadLocal<>();

    /**
     * Set the name of database want to use. If the database is not exist from database, it will automatically create
     * a new database name.
	 *
	 * @return the name of the database to be used.
	 */
    public static void setDatabaseName(String name) {
        context.set(name);
    }
    /**
     * Get the name of database from the thread local. 
     *     
     */
    public static String getDatabaseName() {
        return context.get();
    }
    
    /**
     * Clear the name of database from the thread local. 
     *     
     */
    public static void clear(){
        context.remove();
    }
}
