package LevelDB;

import org.iq80.leveldb.DB;
import org.iq80.leveldb.DBFactory;
import org.iq80.leveldb.DBIterator;
import org.iq80.leveldb.Options;
import org.iq80.leveldb.impl.Iq80DBFactory;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import bftsmart.demo.leveldb.*;
// import bftsmart.demo.map.*;

import static org.iq80.leveldb.impl.Iq80DBFactory.bytes;

// use bft db directly
// public class LevelDbUtil extends DBClient{
// }

// use local file as db
public class LevelDbUtil extends LevelDbUtilLocal{}
