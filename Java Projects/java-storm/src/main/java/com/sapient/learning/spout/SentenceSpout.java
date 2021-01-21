package com.sapient.learning.spout;

import java.util.Map;

import org.apache.storm.spout.SpoutOutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseRichSpout;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Values;

public class SentenceSpout extends BaseRichSpout {
	
	private static final long serialVersionUID = 5731326357676384336L;
	
	private SpoutOutputCollector collector;
	
	private String[] sentences = { 
			"my dog has fleas", 
			"i like cold beverages", 
			"the dog ate my homework",
			"don't have a cow man", 
			"i don't think i like fleas" };
	
	private int index = 0;

	public void declareOutputFields(OutputFieldsDeclarer declarer) {
		declarer.declare(new Fields("sentence"));
	}

	@SuppressWarnings("rawtypes")
	public void open(Map config, TopologyContext context, SpoutOutputCollector collector) {
		this.collector = collector;
	}

	public void nextTuple() {
		
		this.collector.emit(new Values(sentences[index]));
		System.out.println("Next Tuple: " + sentences[index]);
		index++;
		if (index >= sentences.length) {
			index = 0;
		}
		//Utils.sleep(1000);
	}
}