package com.ibrahimRamadan.forexAPI.service;

import com.ibrahimRamadan.forexAPI.DTO.UserDto;
import com.ibrahimRamadan.forexAPI.entity.UserEntity;
import com.ibrahimRamadan.forexAPI.exception.ResourceNotFoundException;
import com.ibrahimRamadan.forexAPI.repository.UserRepository;
import io.micrometer.core.instrument.Timer;
import io.micrometer.core.instrument.simple.SimpleMeterRegistry;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ModelMapper modelMapper;

    private Timer timer;
    @Autowired
    private SimpleMeterRegistry simpleMeterRegistry;

    @Autowired
    private static CopyOnWriteArrayList<Double> responseTimes = new CopyOnWriteArrayList<>();



    public UserDto getUserByUsername(String username)
    {
        // Time calculation thing
        timer = simpleMeterRegistry.timer("greetings.timer");
        Timer.Sample sample = Timer.start();


        Optional<UserEntity> userOptional = userRepository.findByUsername(username);
        if (userOptional.isEmpty())
        {
            throw new ResourceNotFoundException("User not found for this username :: " + username);
        }

        // End of time calculation thing
        double responseTimeInMilliSeconds = timer.record(() -> sample.stop(timer) / 1000);
        responseTimes.add(responseTimeInMilliSeconds);

        return modelMapper.map(userOptional.get(), UserDto.class);
    }

    public UserDto saveUser(UserDto userDto)
    {
        UserEntity user = modelMapper.map(userDto, UserEntity.class);
        UserEntity savedUser = userRepository.save(user);
        return modelMapper.map(savedUser, UserDto.class);
    }

    private static void writeResponseTimesToFile(double[] responseTimes) {
        try (FileWriter writer = new FileWriter("UserService_response_times.txt", true)) {
            for (double responseTime : responseTimes) {
                writer.write(responseTime + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private static void writeListToFile(List<Double> responseTimes) {
        double[] responseTimesArray = responseTimes.stream().mapToDouble(Double::doubleValue).toArray();
        writeResponseTimesToFile(responseTimesArray);
    }

    public void getResult()
    {
        writeListToFile(responseTimes);
        // Clearing the array
        responseTimes.clear();
    }

}

