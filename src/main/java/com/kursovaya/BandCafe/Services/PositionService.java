package com.kursovaya.BandCafe.Services;

import com.kursovaya.BandCafe.Entities.Position;
import com.kursovaya.BandCafe.Repos.PositionRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PositionService {

    @Autowired
    PositionRepo positionRepo;

    public void addMemberPosition(String memberID, Integer positionCode) {
        positionRepo.addMemberPosition(memberID, positionCode);
    }

    public void deleteMemberPosition(String memberID, Integer positionCode) {
        positionRepo.deleteMemberPosition(memberID, positionCode);
    }


    public List<Position> getAllPositions() {
        return positionRepo.getAllPositions();
    }
    public List<Position> getAllMemberPositions(String memberID) {
        return positionRepo.getAllMemberPositions(memberID);
    }

    public List<Integer> getAllMemberPositionsCodes(String memberID) {
        return positionRepo.getAllMemberPositionsCodes(memberID);
    }
}
