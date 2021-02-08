package com.webmaple.common.util;

import ch.ethz.ssh2.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;

/**
 * @author lyifee
 * on 2021/2/8
 */
public class SSHUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(SSHUtil.class);

    /**
     * execute command
     * @param conn ssh connection
     * @param command command to exec
     */
    public static void execCommand(Connection conn, String command) {
        Session sess = null;
        try {
            sess = conn.openSession();
            sess.execCommand(command);

            InputStream stdout = new StreamGobbler(sess.getStdout());
            InputStream stderr = new StreamGobbler(sess.getStderr());
            BufferedReader stdoutReader = new BufferedReader(new InputStreamReader(stdout));
            BufferedReader stderrReader = new BufferedReader(new InputStreamReader(stderr));
            LOGGER.info("========== stdout: ===========" );
            String line;
            while ((line = stdoutReader.readLine()) != null) {
                LOGGER.info(line);
            }
            LOGGER.info("========== stderr: ===========" );
            while ((line = stderrReader.readLine()) != null) {
                LOGGER.info(line);
            }

        } catch (Exception e) {
            LOGGER.error("exec_command_err:", e);

        } finally {
            if (sess != null) {
                sess.close();
            }
        }
    }

    /**
     * upload file to server
     * @param conn connection
     * @param filepath local filepath
     */
    public static void uploadFile(Connection conn, String filepath) {
        SCPClient client = null;
        try {
            String filename = "app.jar";
            client = new SCPClient(conn);
            client.put(filepath, "/root/");

        } catch (Exception e) {
            LOGGER.error("upload_file_exception:", e);
        }
    }

    /**
     * get ssh connection of host
     * @param hostname hostname or ip
     * @param username username of server
     * @param password password of username
     * @return connection
     */
    public static Connection getConnection(String hostname, String username, String password) {
        try {
            Connection conn = new Connection(hostname);
            conn.connect();
            boolean isAuthenticated = conn.authenticateWithPassword(username, password);
            if (!isAuthenticated) {
                LOGGER.error("authenticate_fail");
                return null;
            }
            return conn;

        } catch (IOException e) {
            LOGGER.error("authenticate_exception:", e);
            return null;
        }
    }

    /**
     * close connection
     * @param conn connection
     */
    public static void close(Connection conn) {
        if (conn != null) {
            conn.close();
        }
    }
}
