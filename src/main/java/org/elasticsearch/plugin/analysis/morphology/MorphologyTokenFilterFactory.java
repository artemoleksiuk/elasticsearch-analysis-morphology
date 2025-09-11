package org.elasticsearch.plugin.analysis.morphology;

import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.morphology.LuceneMorphology;
import org.apache.lucene.morphology.analyzer.MorphologyFilter;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.env.Environment;
import org.elasticsearch.index.IndexSettings;
import org.elasticsearch.index.analysis.AbstractTokenFilterFactory;

/**
 * Russian/english morphology token filter factory
 */
public class MorphologyTokenFilterFactory extends AbstractTokenFilterFactory {

    private final LuceneMorphology luceneMorph;

    public MorphologyTokenFilterFactory(IndexSettings indexSettings, Environment environment, String name,
                                        Settings settings, LuceneMorphology englishLuceneMorphology) {
        super(name);
        luceneMorph = englishLuceneMorphology;
    }

    @Override
    public TokenStream create(TokenStream tokenStream) {
        return new MorphologyFilter(tokenStream, luceneMorph);
    }
}