package com.sanvalero.ejemplofxml;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.sanvalero.ejemplofxml.domain.Coche;
import org.bson.codecs.configuration.CodecRegistries;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CocheDAO {

    private MongoClient mongoClient;
    private MongoDatabase db;
    private static final String DATABASE_NAME = "coches";

//    public void conectar() {
////        mongoClient = new MongoClient();
//        mongoClient = new MongoClient("localhost", 27017);
//        db = mongoClient.getDatabase(DATABASE_NAME);
//    }

    public void conectar() {
        CodecRegistry pojoCodecRegistry = CodecRegistries.fromRegistries(MongoClient.getDefaultCodecRegistry(),
                CodecRegistries.fromProviders(PojoCodecProvider.builder().automatic(true).build()));
        MongoClient cliente = new MongoClient("localhost",
                MongoClientOptions.builder().codecRegistry(pojoCodecRegistry).build());
        db = cliente.getDatabase(DATABASE_NAME);
    }

    public void desconectar() {
        mongoClient.close();
    }

    public void guardarCoche(Coche coche) {
//        Document documento = new Document()
//                .append("marca", coche.getMarca())
//                .append("modelo", coche.getModelo())
//                .append("matricula", coche.getMatricula())
//                .append("tipo", coche.getTipo());
//        db.getCollection(DATABASE_NAME).insertOne(documento);

        MongoCollection<Coche> collection = db.getCollection("coches", Coche.class);
        collection.insertOne(coche);
    }

    public void eliminarCoche(Coche coche) throws SQLException {

    }

    public void modificarCoche(Coche cocheAntiguo, Coche cocheNuevo) throws SQLException {

    }

    public List<Coche> obtenerCoches() {
//        List<Coche> coches = new ArrayList<>();
////
////        Document documento = new Document();
////        FindIterable findIterable = db.getCollection(DATABASE_NAME).find(documento);
////
////        Iterator<Document> iter = findIterable.iterator();
////        while (iter.hasNext()) {
////            Document doc = iter.next();
////            Coche coche = new Coche();
////            coche.setId(doc.getObjectId("_id"));
////            coche.setModelo(doc.getString("modelo"));
////            coche.setMarca(doc.getString("marca"));
////            coche.setMatricula(doc.getString("matricula"));
////            coche.setTipo(doc.getString("tipo"));
////            coches.add(coche);
////        }
////
////        return coches;

        MongoCollection<Coche> collection = db.getCollection("coches", Coche.class);
        return collection.find().into(new ArrayList<>());
    }

    public boolean existeCoche(String matricula) {
        return false;
    }
}
