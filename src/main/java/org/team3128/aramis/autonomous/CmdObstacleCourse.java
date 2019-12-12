package org.team3128.aramis.autonomous;

import org.team3128.common.drive.SRXTankDrive;
import org.team3128.common.util.enums.Direction;
import org.team3128.common.util.units.Length;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class CmdObstacleCourse extends CommandGroup {
    public CmdObstacleCourse() {
        SRXTankDrive drive = SRXTankDrive.getInstance();
        addSequential(drive.new CmdDriveStraight(98 * Length.in, 0.5, 10000));
        addSequential(drive.new CmdInPlaceTurn(45, Direction.RIGHT, 0.5, 10000));
        addSequential(drive.new CmdDriveStraight(38 * Length.in, 0.5, 10000));
        addSequential(drive.new CmdInPlaceTurn(105, Direction.LEFT, 0.5, 100000));
        addSequential(drive.new CmdDriveStraight(58*Length.in, 0.5, 100000));
        addSequential(drive.new CmdArcTurn(47, 90, Direction.RIGHT, 0.5, 10000));
        addSequential(drive.new CmdDriveStraight(37*Length.in, 0.5, 10000));
        addSequential(drive.new CmdInPlaceTurn(45, Direction.RIGHT, 0.5, 10000));
        addSequential(drive.new CmdDriveStraight(27*Length.in, 0.5, 10000));
        addSequential(drive.new CmdInPlaceTurn(90, Direction.RIGHT, 0.5, 100000));
        addSequential(drive.new CmdDriveStraight(21*Length.in, 0.5, 10000));
        addSequential(drive.new CmdInPlaceTurn(90, Direction.LEFT, 0.5, 100000));
        addSequential(drive.new CmdDriveStraight(116*Length.in, 0.5, 100000));
    }
    

    //98 inches
    //45 right
    //38 inches
    //105 degrees left
    //58 inches
    //90 degrees, radius = 47 (right)
    //37 inches
    //45 right
    //27 inches
    //90 right
    //21 inches
    //90 left
    //116 inches


}