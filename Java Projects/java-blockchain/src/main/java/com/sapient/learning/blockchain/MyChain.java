package com.sapient.learning.blockchain;

import java.util.ArrayList;

import com.sapient.learning.util.StringUtil;

public class MyChain {

	public static ArrayList<Block> blockchain = new ArrayList<Block>();
	public static int difficulty = 5;

	public static void main(String[] args) {
		// add our blocks to the blockchain ArrayList:
		System.out.println("Trying to Mine block 1... ");
		addBlock(new Block("The first block", "0"));

		System.out.println("Trying to Mine block 2... ");
		addBlock(new Block("The second block", blockchain.get(blockchain.size() - 1).hash));

		System.out.println("Trying to Mine block 3... ");
		addBlock(new Block("The third block", blockchain.get(blockchain.size() - 1).hash));

		System.out.println("\nBlockchain is Valid: " + isChainValid());

		String blockchainJson = StringUtil.getJson(blockchain);
		System.out.println("\nThe block chain: ");
		System.out.println(blockchainJson);
	}

	public static Boolean isChainValid() {
		Block currentBlock;
		Block previousBlock;
		String hashTarget = new String(new char[difficulty]).replace('\0', '0');

		// Loop through blockchain to check hashes:
		for (int i = 1; i < blockchain.size(); i++) {
			currentBlock = blockchain.get(i);
			previousBlock = blockchain.get(i - 1);
			// Compare registered hash and calculated hash:
			if (!currentBlock.hash.equals(currentBlock.calculateHash())) {
				System.out.println("Current Hashes not equal");
				return false;
			}
			// Compare previous hash and registered previous hash
			if (!previousBlock.hash.equals(currentBlock.previousHash)) {
				System.out.println("Previous Hashes not equal");
				return false;
			}
			// Check if hash is solved
			if (!currentBlock.hash.substring(0, difficulty).equals(hashTarget)) {
				System.out.println("This block hasn't been mined");
				return false;
			}

		}
		return true;
	}

	public static void addBlock(Block newBlock) {
		newBlock.mineBlock(difficulty);
		blockchain.add(newBlock);
	}
}