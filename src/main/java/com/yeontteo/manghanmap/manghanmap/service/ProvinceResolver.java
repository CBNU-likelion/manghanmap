package com.yeontteo.manghanmap.manghanmap.service;

import com.yeontteo.manghanmap.manghanmap.domain.University;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class ProvinceResolver {

    private static final Set<String> CHUNGBUK_UNIVERSITIES = Set.of(
            "청주대",
            "충북대",
            "한국교통대"
    );

    private static final Set<String> JEONNAM_UNIVERSITIES = Set.of(
            "순천대"
    );

    private static final Set<String> GYEONGNAM_UNIVERSITIES = Set.of(
            "경상국립대",
            "경성대",
            "국립창원대",
            "동아대"
    );

    public String resolveProvince(University university) {
        String province = university.getProvince();
        String name = university.getName();

        if (province == null || province.isBlank()) {
            return "";
        }

        return switch (province) {
            case "경기" -> "경기도";
            case "강원" -> "강원도";
            case "제주" -> "제주특별자치도";
            case "서울" -> "서울특별시";
            case "충청" -> CHUNGBUK_UNIVERSITIES.contains(name) ? "충청북도" : "충청남도";
            case "전라" -> JEONNAM_UNIVERSITIES.contains(name) ? "전라남도" : "전라북도";
            case "경상" -> GYEONGNAM_UNIVERSITIES.contains(name) ? "경상남도" : "경상북도";
            default -> province;
        };
    }

    public boolean matchesProvince(University university, String provinceQuery) {
        if (provinceQuery == null || provinceQuery.isBlank()) {
            return true;
        }

        String resolvedProvince = resolveProvince(university);
        String normalizedQuery = normalizeProvinceQuery(provinceQuery);

        if (normalizedQuery != null) {
            return resolvedProvince.equals(normalizedQuery);
        }

        return switch (provinceQuery.trim()) {
            case "충청" -> resolvedProvince.startsWith("충청");
            case "전라" -> resolvedProvince.startsWith("전라");
            case "경상" -> resolvedProvince.startsWith("경상");
            default -> resolvedProvince.equals(provinceQuery.trim());
        };
    }

    private String normalizeProvinceQuery(String provinceQuery) {
        return switch (provinceQuery.trim()) {
            case "경기", "경기도" -> "경기도";
            case "강원", "강원도", "강원특별자치도" -> "강원도";
            case "충북", "충청북도" -> "충청북도";
            case "충남", "충청남도" -> "충청남도";
            case "전북", "전라북도", "전북특별자치도" -> "전라북도";
            case "전남", "전라남도" -> "전라남도";
            case "경북", "경상북도" -> "경상북도";
            case "경남", "경상남도" -> "경상남도";
            case "제주", "제주도", "제주특별자치도" -> "제주특별자치도";
            case "서울", "서울특별시" -> "서울특별시";
            default -> null;
        };
    }
}
