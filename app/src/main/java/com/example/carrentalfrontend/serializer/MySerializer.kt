package com.example.carrentalfrontend.serializer

import androidx.datastore.CorruptionException
import androidx.datastore.Serializer
import com.example.carrentalfronted.Person
import com.google.protobuf.InvalidProtocolBufferException
import java.io.InputStream
import java.io.OutputStream

class MySerializer : Serializer<Person> {
    override fun readFrom(input: InputStream): Person {
        try {
            return Person.parseFrom(input)
        } catch (e: InvalidProtocolBufferException) {
            throw CorruptionException("Cannot read proto.", e)
        }
    }

    override fun writeTo(t: Person, output: OutputStream) {
        t.writeTo(output)
    }

}