package com.poc.jars.layer2;

/**
 * Layer 2 JAR: DB layer that performs a comparison against the DB
 * using the connection string received from Layer 3.
 */
public class DbService {

    /**
     * Connect using the given DB connection and run a dummy comparison.
     *
     * @param dbConnection e.g. layer2_qa_db
     * @return true when comparison succeeds (always true in this POC)
     */
    public boolean compare(String dbConnection) {
        System.out.println("[Layer2] Connected using <" + dbConnection + ">");
        System.out.println("[Layer2] Comparison done against <" + dbConnection + ">");
        return true;
    }
}
