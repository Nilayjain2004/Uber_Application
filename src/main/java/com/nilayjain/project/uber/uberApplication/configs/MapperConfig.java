package com.nilayjain.project.uber.uberApplication.configs;

import com.nilayjain.project.uber.uberApplication.dto.PointDto;
import com.nilayjain.project.uber.uberApplication.utils.GeometryUtil;
import org.locationtech.jts.geom.Point;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MapperConfig {

//@Configuration → means this class defines Spring beans.
//@Bean → tells Spring “register this method’s return value as a bean in the application context”.
//So now anywhere in your app you can use:

//Use r user = userRepository.findById(1L).get();

// Convert Entity → DTO
//User Dto userDto = modelMapper.map(user, UserDto.class);

// Convert DTO → Entity
//User userEntity = modelMapper.map(userDto, User.class);

    @Bean
    public ModelMapper modelMapper(){

        ModelMapper mapper = new ModelMapper();

        mapper.typeMap(PointDto.class, Point.class).setConverter(context -> {
            PointDto pointDto = context.getSource();
            return GeometryUtil.createPoint(pointDto);
        });

        mapper.typeMap(Point.class,PointDto.class).setConverter(context -> {
            Point point = context.getSource();
            double coordinates[] = {
                    point.getX(),
                    point.getY()
            };
            return new PointDto(coordinates);
        });

        return mapper;
    }
}
