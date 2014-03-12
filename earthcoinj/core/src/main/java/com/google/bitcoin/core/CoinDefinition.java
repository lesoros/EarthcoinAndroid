package com.google.bitcoin.core;

import java.math.BigInteger;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;
import java.util.Vector;

import static com.google.bitcoin.core.Utils.COIN;

/**
 * Created with IntelliJ IDEA.
 * User: HashEngineering
 * Date: 8/13/13
 * Time: 7:23 PM
 * To change this template use File | Settings | File Templates.
 */
public class CoinDefinition {


    public static final String coinName = "Earthcoin";
    public static final String coinTicker = "EAC";
    public static final String coinURIScheme = "earthcoin";
    public static final String cryptsyMarketId = "139";
    public static final String cryptsyMarketCurrency = "BTC";


    public static final String BLOCKEXPLORER_BASE_URL_PROD = "http://earthchain.info/";
    public static final String BLOCKEXPLORER_BASE_URL_TEST = "http://earthchain.info/";

    public static final String DONATION_ADDRESS = "ebrPC5c1RddyCrWUg1q8EbAMcsnT5znyTq";  //Scottlle aka xploited

    enum CoinHash {
        SHA256,
        scrypt
    };
    public static final CoinHash coinHash = CoinHash.scrypt;
    //Original Values


    public static final int TARGET_TIMESPAN = (int)(60 * 30);    // 30 minutes
    public static final int TARGET_SPACING = (int)(60 * 1);                  // 1 minutes per block.

    public static final int INTERVAL = TARGET_TIMESPAN / TARGET_SPACING;  //108 blocks


    public static final int getInterval(int height, boolean testNet) {
            return INTERVAL;      //108
    }
    public static final int getTargetTimespan(int height, boolean testNet) {
            return TARGET_TIMESPAN;    //72 min
    }

    public static int spendableCoinbaseDepth = 30; //main.h: static const int COINBASE_MATURITY
    //public static final int MAX_MONEY = 200000000;                 //main.h:  MAX_MONEY
    public static final BigInteger MAX_MONEY = new BigInteger("13500000000", 10).multiply(COIN);
    public static final String MAX_MONEY_STRING = "13500000000";     //main.h:  MAX_MONEY
    public static final double SERVICE_TAX_PERCENTAGE=0.02;
    public static final BigDecimal TOTAL_GENERATION = new BigDecimal(MAX_MONEY);

    public static final BigInteger DEFAULT_MIN_TX_FEE = BigInteger.valueOf(1000000);   // MIN_TX_FEE
    public static final BigInteger DUST_LIMIT = Utils.CENT; //main.h CTransaction::GetMinFee        0.01 coins

    public static final int PROTOCOL_VERSION = 60002;          //version.h PROTOCOL_VERSION
    public static final int MIN_PROTOCOL_VERSION = 209;        //version.h MIN_PROTO_VERSION

    public static final boolean supportsBloomFiltering = false; //Requires PROTOCOL_VERSION 70000 in the client

    public static final int Port    = 12024;       //protocol.h GetDefaultPort(testnet=false)
    public static final int TestPort = 12025;     //protocol.h GetDefaultPort(testnet=true)

    //
    //  Production
    //
    public static final int AddressHeader = 93;             //base58.h CBitcoinAddress::PUBKEY_ADDRESS
    public static final int p2shHeader = 5;             //base58.h CBitcoinAddress::SCRIPT_ADDRESS

    public static final int dumpedPrivateKeyHeader = 128;   //common to all coins
    public static final long PacketMagic = 0xc0dbf1fd;      //0xc0, 0xdb, 0xf1, 0xfd

    //Genesis Block Information from main.cpp: LoadBlockIndex
    static public long genesisBlockDifficultyTarget = (0x1e0ffff0L);         //main.cpp: LoadBlockIndex
    static public long genesisBlockTime = 1386746168L;                       //main.cpp: LoadBlockIndex
    static public long genesisBlockNonce = (12468024);                         //main.cpp: LoadBlockIndex
    static public String genesisHash = "21717d4df403301c0538f1cb9af718e483ad06728bbcd8cc6c9511e2f9146ced"; //main.cpp: hashGenesisBlock
    static public int genesisBlockValue = 0;                                                              //main.cpp: LoadBlockIndex
    //taken from the raw data of the block explorer
    static public String genesisXInBytes = "04ffff001d01044c5920446563656d6265722031392c20323031332097204172726573742c2073747269702d736561726368206f6620496e6469616e206469706c6f6d617420696e204e657720596f726b207472696767657273207570726f61722e";   //"Digitalcoin, A Currency for a Digital Age"
    static public String genessiXOutBytes = "04dcba12349012341234900abcd12223abcd455abcd77788abcd000000aaaaabbbbbcccccdddddeeeeeff00ff00ff00ff001234567890abcdef0022446688abc11";

    //net.cpp strDNSSeed
    static public String[] dnsSeeds = new String[] {
              
    };

    //
    // TestNet - earthcoin - not tested or setup
    //
    public static final boolean supportsTestNet = false;
    public static final int testnetAddressHeader = 111;             //base58.h CBitcoinAddress::PUBKEY_ADDRESS_TEST
    public static final int testnetp2shHeader = 196;             //base58.h CBitcoinAddress::SCRIPT_ADDRESS_TEST
    public static final long testnetPacketMagic = 0xfcc1b7dc;      //0xfc, 0xc1, 0xb7, 0xdc
    public static final String testnetGenesisHash = "5e039e1ca1dbf128973bf6cff98169e40a1b194c3b91463ab74956f413b2f9c8";
    static public long testnetGenesisBlockDifficultyTarget = (0x1e0ffff0L);         //main.cpp: LoadBlockIndex
    static public long testnetGenesisBlockTime = 999999L;                       //main.cpp: LoadBlockIndex
    static public long testnetGenesisBlockNonce = (99999);                         //main.cpp: LoadBlockIndex


    //main.cpp GetBlockValue(height, fee)
    public static final BigInteger GetBlockReward(int height)
    {
        int COIN = 1;
        BigInteger nSubsidy = Utils.toNanoCoins(10000, 0);

        if(height == 1)
        {
            BigDecimal bdresult = TOTAL_GENERATION.multiply(new BigDecimal(SERVICE_TAX_PERCENTAGE));
            BigInteger correct = bdresult.toBigInteger();
            nSubsidy=correct;
            return nSubsidy;
        }

        int nHeightM = height % 525600;
        double phase = ((double)nHeightM) / 525600.0 * 2.0 * Math.PI;

        BigInteger factor = new BigInteger("2000");
        BigDecimal sinphase = new BigDecimal(Math.sin(phase));
        BigDecimal test = new BigDecimal(factor);

        test = test.multiply(sinphase);
        BigInteger diffCurve = test.toBigInteger();
        nSubsidy = nSubsidy.add(diffCurve);


        int day =  height / 1440 + 1;

        if(day % 31 == 0)
        {
            nSubsidy = Utils.toNanoCoins(50000, 0);

        }
        else if(day % 14 == 0)
        {
            nSubsidy = Utils.toNanoCoins(20000,0);
        }
        else if(day == 1)
        {
            nSubsidy = Utils.toNanoCoins(50000, 0);
        }
        else if(day == 2)
        {
            nSubsidy = Utils.toNanoCoins(30000, 0);
        }
        else if(day == 3)
        {
            nSubsidy = Utils.toNanoCoins(20000,0);
        }

        nSubsidy = nSubsidy.shiftRight(height / 525600);

        return nSubsidy;
    }

    public static int subsidyDecreaseBlockCount = 525600;     //main.cpp GetBlockValue(height, fee)

    public static BigInteger proofOfWorkLimit = Utils.decodeCompactBits(0x1e0fffffL);  //main.cpp bnProofOfWorkLimit (~uint256(0) >> 20); // earthcoin: starting difficulty is 1 / 2^12

    static public String[] testnetDnsSeeds = new String[] {
          "not supported"
    };
    //from main.h: CAlert::CheckSignature
    public static final String SATOSHI_KEY = "040184710fa689ad5023690c80f3a49c8f13f8d45b8c857fbcbc8bc4a8e4d3eb4b10f4d4604fa08dce601aaf0f470216fe1b51850b4acf21b179c45070ac7b03a9";
    public static final String TESTNET_SATOSHI_KEY = "";

    /** The string returned by getId() for the main, production network where people trade things. */
    public static final String ID_MAINNET = "org.bitcoin.production";
    /** The string returned by getId() for the testnet. */
    public static final String ID_TESTNET = "org.bitcoin.test";
    /** Unit test network. */
    public static final String ID_UNITTESTNET = "com.google.bitcoin.unittest";

    //checkpoints.cpp Checkpoints::mapCheckpoints
    public static void initCheckpoints(Map<Integer, Sha256Hash> checkpoints)
    {
					//checkpoints.put( 0, new Sha256Hash("0x7497ea1b465eb39f1c8f507bc877078fe016d6fcb6dfad3a64c98dcc6e1e8496"));	
    }


}
