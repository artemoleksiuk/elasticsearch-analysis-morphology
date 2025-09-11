package org.elasticsearch.plugin.analysis.morphology;

import org.apache.lucene.morphology.LuceneMorphology;
import org.apache.lucene.morphology.analyzer.MorphologyAnalyzer;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.env.Environment;
import org.elasticsearch.index.analysis.AbstractIndexAnalyzerProvider;

/**
 * Provider for russian/english morphology analyzer
 */
public class MorphologyAnalyzerProvider extends AbstractIndexAnalyzerProvider<MorphologyAnalyzer> {

    private final MorphologyAnalyzer analyzer;

    public MorphologyAnalyzerProvider(Environment environment, String name,
                                      Settings settings, LuceneMorphology luceneMorphology) {
        super(name);
        analyzer = new MorphologyAnalyzer(luceneMorphology);
    }

    @Override
    public MorphologyAnalyzer get() {
        return this.analyzer;
    }
}