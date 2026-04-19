package com.user.Repository.FeingRepository;
import com.user.Dto.AuthDto.Oauth2Info;
import com.user.Dto.AuthDto.UserInfo;
import com.user.Dto.AuthDto.UserInfoForToken;
import com.user.Entity.UserCredentials;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthFeignRepository extends JpaRepository<UserCredentials,Long> {

    @Query("""
            
            select new com.user.Dto.AuthDto.UserInfo(
                      u.userName,
                      u.password
            
            ) from UserCredentials u where u.userName = :userName
            
            
            """)
    UserInfo getUserCredentialDetails(@Param("userName") String userName);


    @Query("""
            select new  com.user.Dto.AuthDto.UserInfoForToken(
                 u.userName,
                  u.id
            ) from UserCredentials u where u.userName = :userName
            """)
    UserInfoForToken getUserInfoForToken(@Param("userName") String userName);


    @Query("""
            select new  com.user.Dto.AuthDto.Oauth2Info(
            u.userName,
            u.providerId,
            u.providerName
            ) from UserCredentials u where  u.userName = :userName And u.providerId = :providerId And u.providerName = :providerName
            """)
    Oauth2Info getOauth2Info(@Param("userName") String userName, @Param("providerId") String providerId, @Param("providerName") String providerName);

    UserCredentials findByUserName(String userName);

    @Query("""
            select new  com.user.Dto.AuthDto.Oauth2Info(
            u.userName,
            u.providerId,
            u.providerName
            ) from UserCredentials u where  u.userName = :userName
            """)
    Oauth2Info getInfo(@Param("userName") String userName);
}
