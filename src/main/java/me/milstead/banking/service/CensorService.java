package me.milstead.banking.service;

import lombok.extern.slf4j.Slf4j;
import me.milstead.banking.error.CensorException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service for checking strings for offensive words.
 */
@Service
@Slf4j
public class CensorService {

    // Obviously these are real offensive words
    // TODO: To save blushes one could use hashes
    private static final List<String> OFFENSIVE_WORDS = List.of (
            "door",
            "trade",
            "orange",
            "clear",
            "flax"
    );

    /**
     * Validate whether the specified string contains any offensive words.
     * @param str The string to check.
     * @throws CensorException If there are any offensive words.
     */
    public void validate(String str) {
        if (log.isTraceEnabled()) {
            log.trace("str = {}", str);
        }
        if (StringUtils.isBlank(str)) {
            return;
        }
        String[] words = str.split("\\s");
        for (String word : words) {
            if (OFFENSIVE_WORDS.contains(word.toLowerCase())) {
                if (log.isTraceEnabled()) {
                    log.trace("str, {}, contains offensive word, {}", str, word);
                }
                throw new CensorException();
            }
        }
    }

}
