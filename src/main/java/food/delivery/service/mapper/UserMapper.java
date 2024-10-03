//package food.delivery.service.mapper;
//
//
//import food.delivery.dto.UserDto;
//import food.delivery.model.User;
//
//public class UserMapper {
//
//    public static UserDto toDto(User user){
//        return UserDto.builder()
//                .id(user.getId())
//                .fullName(user.getFullName())
//                .address(user.getAddress())
//                .username(user.getUsername())
//                .phoneNum1(user.getPhoneNum1())
//                .phoneNum2(user.getPhoneNum2())
//                .telegramId(user.getTelegramId())
//                .build();
//    }
//
//    public static User toEntity(UserDto userDto){
//        return User.builder()
//                .id(userDto.getId())
//                .fullName(userDto.getFullName())
//                .address(userDto.getAddress())
//                .username(userDto.getUsername())
//                .phoneNum1(userDto.getPhoneNum1())
//                .phoneNum2(userDto.getPhoneNum2())
//                .build();
//    }
//
//
//}