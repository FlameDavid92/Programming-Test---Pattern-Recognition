package it.welld.patternrecognition.PatternRecognition;

import org.springframework.cache.annotation.CacheConfig;
import org.springframework.stereotype.Service;

@Service
@CacheConfig(cacheNames= {"patternrecognition"})
public class PatternRecognitionService {

}
