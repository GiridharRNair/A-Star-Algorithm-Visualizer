class Solution(object):
    def canAliceWin(self, n):
        """
        :type n: int
        :rtype: bool
        """
        remove = 10
        while remove <= n:
            n -= remove
            remove -= 1
        return remove % 2 == 1

        