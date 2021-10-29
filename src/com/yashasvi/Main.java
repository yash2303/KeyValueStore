package com.yashasvi;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.yashasvi.activity.SearchService;
import com.yashasvi.activity.StorageService;
import com.yashasvi.exception.DataTypeException;
import com.yashasvi.model.Command;
import com.yashasvi.model.CommandType;
import com.yashasvi.model.DataStore;
import com.yashasvi.model.InMemoryDataStore;
import com.yashasvi.model.Pair;
import com.yashasvi.util.DataTypeIdentifier;

public class Main {

    public static void main(String[] args) throws IOException {
        DataStore dataStore = new InMemoryDataStore();
        DataTypeIdentifier dataTypeIdentifier = new DataTypeIdentifier();
        SearchService searchService = new SearchService(dataTypeIdentifier, dataStore);
        StorageService storageService = new StorageService(dataStore, dataTypeIdentifier);

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Storage Service successfully started !!");
        while (true) {
            String input = reader.readLine();
            Command command = new Command(input);
            if (CommandType.PUT.getType().equals(command.getCommandName())) {
                List<Pair<String, String>> value = new ArrayList<>();
                List<String> params = command.getParams();
                for (int i = 1; i < params.size(); i += 2) {
                    value.add(new Pair<>(params.get(i), params.get(i + 1)));
                }
                try {
                    storageService.put(params.get(0), value);
                    System.out.println("PUT Successful");
                } catch (DataTypeException e) {
                    System.out.println(e.getMessage());
                }
            } else if (CommandType.GET.getType().equals(command.getCommandName())) {
                Map<String, String> output = storageService.get(command.getParams().get(0));
                if (output == null) {
                    System.out.println("No entry found for sde_kickstart");
                } else {
                    System.out.println(output);
                }
            } else if (CommandType.DELETE.getType().equals(command.getCommandName())) {
                storageService.delete(command.getParams().get(0));
                System.out.println("DELETE Successful");
            } else if (CommandType.SEARCH.getType().equals(command.getCommandName())) {
                List<String> params = command.getParams();
                try {
                    List<String> keys = searchService.search(params.get(0), params.get(1));
                    System.out.println(keys);
                } catch (DataTypeException e) {
                    System.out.println(e.getMessage());
                }
            } else if (CommandType.KEYS.getType().equals(command.getCommandName())) {
                List<String> keys = storageService.keys();
                System.out.println(keys);
            } else if (CommandType.EXIT.getType().equals(command.getCommandName())) {
                System.out.println("Shutting down storage service !");
                break;
            }
        }
    }
}
