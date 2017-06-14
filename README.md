# Eight-Puzzle-Solver

OVERVIEW.

The problem of solving Eight-Puzzle (the smaller version of the well-known 15-puzzle) is
widely explored and realized with a multitude of different approaches on various languages. The
most basic solution is to traverse the graph of positions with so-called breadth-first-search.
However, many interesting search problems happen to have immense graphs, which makes BFS search virtually helpless
For example, the Rubik' Cube graph consists of 4.3 quintillion vertices. In order to solve such
puzzles, one needs to discover more sophisticated approaches, shortcuts, invariants and reasonable
subgoals, which would improve the time-efficiency of the search. Inspired by Thomas Rokicki's use
of algebra and subgoals in solving the Rubik's Cube, I decided to tackle a much smaller problem
(Eight-Puzzle has a graph of 181,440 vertices) with similar tricks. With their help, the resulting program
outputs a solution to a random Eight-Puzzle position in 10 to 100 milliseconds (on average). While the
diameter of the Eigth-Puzzle graph is 31, my solutions' length rarely exceeds 35 moves.

REPRESENTATION.

One of the most intuitive and thus convenient ways to represent any state of the Eight-Puzzle is by
using an even permutation from the set Perm9. For example, the solved position will be written as
[1, 2, 3, 4, 5, 6, 7, 8, 0], where 9 is written as 0 to represent an empty slot.

ALGORITHM.

The underlying idea is to solve any given position of the EightPuzzle in two stages. Thomas Rokicki and
other Cube solvers suggest that setting a subgoal of reaching some subgroup might be useful (Here, the
group would be the set of all even permutations from Perm9). I follow the same approach and, on the first
stage, attempt to bring any given position to the subgroup, which consists of all permutations (positions)
with tokens 1, 2, 3 at their places. That is, I am looking at the subgroup of Perm9 with elements of the
following form: [1, 2, 3, x, x, x, x, x, x]. As soon as I bring a position to the subgroup, the remaining
task is trivial: I need to traverse the graph of the reduced eight-puzzle (puzzle 2x3 with tokens 1, 2, 3
fixed) with BFS, which is done in milliseconds as the number of vertices in this graph is 360. Effective
solution to the stage 1, however, should be accomplished with some other technique. To bring the position to
the subgroup with 1, 2, 3 tokens at their slots, the program refers to a hash table of size 1000. The 
principle is that a reversed set of moves that brings the solved state (the identity permutation) into some
other state A will bring a state B with the same positions of tokens 1, 2, 3 as in A into the subgroup
described above. Thus, a table storing sequences of moves for all possible positions of tokens 1, 2, 3 will
help on stage 1. The slots of tokens 1, 2, 3 in the given position is all we need to know in order to
access an appropriate set of moves that will bring it to the subgroup. The final output should consist of
moves from stage 1 (table value), and the sequence produced by the BFS of the reduced puzzle.

ANALYSIS.

This approach processes any position in 10-100 milliseconds, and the length of the produced suboptimal
solutions is rarely greater than 35 (whereas the diameter of the entire group is 31). Further detailed
statistics are to be discovered.

Author: Artem Vysogorets.
BS Mathematics, University of Massachusetts, Amherst 2019.
Contact via e-mail: avysogorets@umass.edu
GNU General Public Licence 3.0 2017.

Works Referenced: "The Diameter of The Rubik's Cube Group is 20" by Thomas Rokicki.


