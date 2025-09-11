package org.elasticsearch.plugin.analysis.morphology;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.morphology.english.EnglishLuceneMorphology;
import org.apache.lucene.morphology.russian.RussianLuceneMorphology;
import org.elasticsearch.index.analysis.AnalyzerProvider;
import org.elasticsearch.index.analysis.TokenFilterFactory;
import org.elasticsearch.plugins.AnalysisPlugin;
import org.elasticsearch.plugins.Plugin;
import org.elasticsearch.indices.analysis.AnalysisModule;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Plugin for russian/english morphology analyzer
 */
public class AnalysisMorphologyPlugin extends Plugin implements AnalysisPlugin {

    private final RussianLuceneMorphology russianLuceneMorphology;
    private final EnglishLuceneMorphology englishLuceneMorphology;

    public AnalysisMorphologyPlugin() {
        super();
        try {
            russianLuceneMorphology = new RussianLuceneMorphology();
        } catch (IOException ex) {
            throw new IllegalStateException("unable to load russian morphology info", ex);
        }
        try {
            englishLuceneMorphology = new EnglishLuceneMorphology();
        } catch (IOException ex) {
            throw new IllegalStateException("unable to load english morphology info", ex);
        }
    }

    @Override
    public Map<String, AnalysisModule.AnalysisProvider<TokenFilterFactory>> getTokenFilters() {
        Map<String, AnalysisModule.AnalysisProvider<TokenFilterFactory>> extra = new HashMap<>();
        extra.put("russian_morphology", (indexSettings, environment, name, settings) ->
                new MorphologyTokenFilterFactory(indexSettings, environment, name, settings, russianLuceneMorphology));
        extra.put("english_morphology", (indexSettings, environment, name, settings) ->
                new MorphologyTokenFilterFactory(indexSettings, environment, name, settings, englishLuceneMorphology));
        return extra;
    }

    @Override
    public Map<String, AnalysisModule.AnalysisProvider<AnalyzerProvider<? extends Analyzer>>> getAnalyzers() {
        Map<String, AnalysisModule.AnalysisProvider<AnalyzerProvider<? extends Analyzer>>> extra = new HashMap<>();
        extra.put("russian_morphology", (indexSettings, environment, name, settings) ->
                new MorphologyAnalyzerProvider(environment, name, settings, russianLuceneMorphology));
        extra.put("english_morphology", (indexSettings, environment, name, settings) ->
                new MorphologyAnalyzerProvider(environment, name, settings, englishLuceneMorphology));
        return extra;
    }
}