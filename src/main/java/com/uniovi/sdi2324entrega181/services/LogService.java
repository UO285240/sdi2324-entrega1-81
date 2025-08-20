package com.uniovi.sdi2324entrega181.services;

import com.uniovi.sdi2324entrega181.entities.Log;
import com.uniovi.sdi2324entrega181.repositories.LogRepository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class LogService {
    private LogRepository logRepository;

    public LogService(LogRepository logRepository) {
        this.logRepository = logRepository;
    }

    /**
     * Logs a PET type log
     * @param desc String description
     */
    public void logPET(String desc) {
        logRepository.save(new Log("PET", desc));
    }

    /**
     * Logs an ALTA type log
     * @param desc String description
     */
    public void logALTA(String desc) {
        logRepository.save(new Log("ALTA", desc));
    }

    /**
     * Logs a LOGIN-EX type log
     * @param desc String description
     */
    public void logLOGINEX(String desc) {
        logRepository.save(new Log("LOGIN-EX", desc));
    }

    /**
     * Logs a LOGIN-ERR type log
     * @param desc String description
     */
    public void logLOGINERR(String desc) {
        logRepository.save(new Log("LOGIN-ERR", desc));
    }

    /**
     * Logs a LOGOUT type log
     * @param desc String description
     */
    public void logLOGOUT(String desc) {
        logRepository.save(new Log("LOGOUT", desc));
    }

    /**
     * Retrieves all logs from the database
     * @param pageable used for pagination purposes
     * @return Page<Log> Page containing the retrieved logs
     */
    public Page<Log> getLogs(Pageable pageable) {
        return logRepository.findAllOrdered(pageable);
    }

    /**
     * Retrieves all PET logs from the database
     * @param pageable used for pagination purposes
     * @return Page<Log> Page containing the retrieved logs
     */
    public Page<Log> getPETLogs(Pageable pageable) {
        return logRepository.findPETOrdered(pageable);
    }

    /**
     * Retrieves all ALTA logs from the database
     * @param pageable used for pagination purposes
     * @return Page<Log> Page containing the retrieved logs
     */
    public Page<Log> getALTALogs(Pageable pageable) {
        return logRepository.findALTAOrdered(pageable);
    }

    /**
     * Retrieves all LOGIN-EX logs from the database
     * @param pageable used for pagination purposes
     * @return Page<Log> Page containing the retrieved logs
     */
    public Page<Log> getLOGINEXLogs(Pageable pageable) {
        return logRepository.findLOGINEXOrdered(pageable);
    }

    /**
     * Retrieves all LOGIN-ERR logs from the database
     * @param pageable used for pagination purposes
     * @return Page<Log> Page containing the retrieved logs
     */
    public Page<Log> getLOGINERRLogs(Pageable pageable) {
        return logRepository.findLOGINERROrdered(pageable);
    }

    /**
     * Retrieves all LOGOUT logs from the database
     * @param pageable used for pagination purposes
     * @return Page<Log> Page containing the retrieved logs
     */
    public Page<Log> getLOGOUTLogs(Pageable pageable) {
        return logRepository.findLOGOUTOrdered(pageable);
    }

    /**
     * Removes all logs from the database
     */
    public void removeAll() {
        logRepository.deleteAll();
    }
}
