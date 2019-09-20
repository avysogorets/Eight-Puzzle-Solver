# Eight-Puzzle-Solver

OVERVIEW.

The problem of solving Eight-Puzzle (the smaller version of the well-known 15-puzzle) is
widely explored and realized with a multitude of different approaches on various languages. 
One basic solution is to traverse the graph of positions with BFS. However, many interesting
search problems happen to have immense graphs, which makes BFS search unsuitable.
For example, the Rubik' cube graph consists of 4.3 quintillion vertices. In order to solve such
puzzles, one needs to discover more sophisticated approaches that could find suboptimal solutions
in a reasonable time. Inspired by Thomas Rokicki's use of algebra in solving the Rubik's cube, we
tackle a much smaller problem (Eight-Puzzle has a state graph of 181,440 vertices) with similar
techniques. This program outputs instant solutions of length at most 20% greater than optimal.

REPRESENTATION.

We use a permutations to represent states of the puzzle. For example, the solved position is
[1, 2, 3, 4, 5, 6, 7, 8, 0] ('0' represents an empty slot).

ALGORITHM.

The underlying idea is to solve any given position of the EightPuzzle in two stages. Thomas Rokicki and
other cube solvers often set subgoals of reaching certain subgroups of the state graph within the search.
We follow the same approach and, at the first stage, bring any given position to the subgroup consisting 
of all positions with tokens 1, 2, 3 at their places. When this is accomplished, we need to traverse the
graph of the remaining puzzle (2x3) with BFS.

Stage 1 goal is achieved by using a look-up table of size 1000, which stores the shortest paths from any coset
to the subgroup specified above. The key principle is that a set of moves that brings the solved state (identity)
to some other state A, when reversed, will bring any state B with the same positions of tokens 1, 2, 3 as in A
to the desired subgroup. Thus, a look-up table storing sequences of moves for all possible positions of tokens
1, 2, 3 (i.e. for all cosets) will help on stage 1. This table is constructed by traversing the entire state 
graph of the puzzle, however, this has to be done only once.

RESULTS.

This approach instantly processes any random state of the puzzle and the length of the produced suboptimal
solutions rarely exceeds 20% of the optimal length.


This work was supported by the Research Assistance Fellowship grant from the Commonwealth Honors College, Amherst, MA

Artem Vysogorets, B.S. in Mathematics, University of Massachusetts Amherst (2017).
Advisor: Dr. Eric Sommers.

Works Referenced: "The Diameter of The Rubik's Cube Group is 20" by Thomas Rokicki.


