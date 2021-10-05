package com.example.gestionAchat.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;


@Service
@Transactional
public class JournalService {




        public Map<String, String> getIp(final HttpServletRequest request) throws UnknownHostException {
            if (request != null) {
                String RemoteAddr = request.getRemoteAddr();

                Map<String, String> result = new HashMap<String, String>();

                InetAddress addr = InetAddress.getLocalHost();

                String ipAddr = addr.getHostAddress();

                String FORWARDED = "";
                FORWARDED = request.getHeader("X-FORWARDED-FOR");

                result.put("FORWARDED", FORWARDED);
                result.put("RemoteAddr", ipAddr);
                System.out.println(" resuuulet adresse ***"+result);
                return result;
            }
            return null;
        }

    }










