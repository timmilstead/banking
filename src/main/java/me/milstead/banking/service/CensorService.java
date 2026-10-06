package me.milstead.banking.service;

import me.milstead.banking.exception.CensorException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service for checking strings don't contain offensive words.
 */
@Service
public class CensorService {

    // TODO: To save blushes one could use hashes instead
    private static final List<String> OFFENSIVE_WORDS = List.of (
            "door",
            "trade",
            "orange",
            "clear",
            "flax"
    );

    public void validate(String str) {
        if (StringUtils.isBlank(str)) {
            return;
        }
        String[] words = str.split("\\s");
        for (String word : words) {
            if (OFFENSIVE_WORDS.contains(word.toLowerCase())) {
                throw new CensorException();
            }
        }
    }

}
