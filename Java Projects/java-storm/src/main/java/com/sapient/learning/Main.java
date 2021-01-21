package com.sapient.learning;

import org.apache.storm.Config;
import org.apache.storm.LocalCluster;
import org.apache.storm.topology.TopologyBuilder;
import org.apache.storm.tuple.Fields;

import com.sapient.learning.bolt.ReportBolt;
import com.sapient.learning.bolt.SplitSentenceBolt;
import com.sapient.learning.bolt.WordCountBolt;
import com.sapient.learning.spout.SentenceSpout;

public class Main {

	private static final String SENTENCE_SPOUT_ID = "sentence-spout";
	private static final String SPLIT_BOLT_ID = "split-bolt";
	private static final String COUNT_BOLT_ID = "count-bolt";
	private static final String REPORT_BOLT_ID = "report-bolt";
	private static final String TOPOLOGY_NAME = "word-count-topology";

	public static void main(String[] args) throws InterruptedException {

		SentenceSpout spout = new SentenceSpout();
		SplitSentenceBolt splitBolt = new SplitSentenceBolt();
		WordCountBolt countBolt = new WordCountBolt();
		ReportBolt reportBolt = new ReportBolt();

		TopologyBuilder builder = new TopologyBuilder();
		builder.setSpout(SENTENCE_SPOUT_ID, spout);
		// SentenceSpout --> SplitSentenceBolt
		builder.setBolt(SPLIT_BOLT_ID, splitBolt).shuffleGrouping(SENTENCE_SPOUT_ID);
		// SplitSentenceBolt --> WordCountBolt
		builder.setBolt(COUNT_BOLT_ID, countBolt).fieldsGrouping(SPLIT_BOLT_ID, new Fields("word"));
		// WordCountBolt --> ReportBolt
		builder.setBolt(REPORT_BOLT_ID, reportBolt).globalGrouping(COUNT_BOLT_ID);

		Config config = new Config();
		LocalCluster cluster = new LocalCluster();
		cluster.submitTopology(TOPOLOGY_NAME, config, builder.createTopology());
		Thread.sleep(100000);
		cluster.killTopology(TOPOLOGY_NAME);
		cluster.shutdown();

	}
}