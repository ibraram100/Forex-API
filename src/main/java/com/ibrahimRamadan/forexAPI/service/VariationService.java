package com.ibrahimRamadan.forexAPI.service;

// Add a method that returns the last saved item in the db or something

import com.ibrahimRamadan.forexAPI.DTO.VariationDto;
import com.ibrahimRamadan.forexAPI.entity.Variation;
import com.ibrahimRamadan.forexAPI.repository.VariationRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VariationService {
    @Autowired
    private VariationRepository variationRepository;
    @Autowired
    private ModelMapper modelMapper;

    // Returning the last saved variation
    public VariationDto getLastVariation()
    {
        return modelMapper.map(variationRepository.findTopByOrderByTimeStampDesc(),VariationDto.class);
    }
}
