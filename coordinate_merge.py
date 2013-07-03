'''
@author: Parsa Hosseini
'''

import argparse
import collections
import copy

class Coordinate_File():
    def __init__(self):
        pass

'''
High-level class which oversees merging of various coordinates-files into a 
singular matrix data-structure.
@author: Parsa Hosseini
'''
class MergerFactory():
    def __init__(self, files):
        self.files = files
        self.unstruct_counts = [] # contains counts but is not in matrix form
        
    def parse_files(self):
        ''' 
        Parses all the user-provided TFBS coordinates files and stores them in
        a way where they can be easily indexed.
        '''
        try:
            for f in self.files:
                file_handle = open(f)
                for line in file_handle:
                    line = line.strip().split('\t')
                    tfbs_count = 0
                    group, seq_name, tfbs = line[0:3]
                    if len(line) != 3: # first 3 columns are descriptors
                        tfbs_count = len(line[3:]) # thereof is coordinates
                    # save TFBS-specific abundances as a dictionary
                    abundance = {'group': group, 'name': seq_name, 'tfbs':tfbs, 
                           'num':tfbs_count}
                    self.unstruct_counts.append(abundance)
            print(len(self.unstruct_counts))
        except IOError:
            print('Invalid TFBS coordinate file [error]')
        except (IndexError, ValueError):
            print('Please check if input is TFBS coordinate file [error]')
            
    def get_rows(self):
        ''' 
        Generates a singular collection of only unique group names and 
        sequence names. This is because each TFBS Coordinates file has a line
        for every TFBS, therefore if many TFBSs hit that sequence, there will
        be many repetitions of that sequence name. This behavior represents
        these repetitions and only displays each name just once.
        '''
        rows = collections.OrderedDict()
        for i in self.unstruct_counts:
            rows[i['group'] + '\t' + i['name']] = None
        return rows
    
    def bare_columns(self):
        ''' 
        Creates a centralized collection of all the TFBSs found in all the
        TFBS Coordinate files. This data-structure is essentially a dictionary
        because each TFBS and its initial zero-count is the key and value
        respectively. Each sequence in the matrix will have its own unique 
        copy of this data-structure, providing the ability for the sequence to
        easily store and keep-track of sequence-specific TFBS abundance. 
        '''
        columns = {}
        for i in self.unstruct_counts:
            tfbs = i['tfbs']
            if tfbs not in columns: # if TFBS not in map, add it
                columns[tfbs] = 0 # give it a unique integer ID
        return columns
        
    def build_skeleton(self):
        cols = self.bare_columns()
        rows = self.get_rows()
        for key in rows:
            rows[key] = copy.deepcopy(cols)

if __name__ == '__main__':
    desc = ''' 
    Upon running Marina, you have the option of saving "TFBS Coordinates" 
    within the File menu. The resultant file contains exact indices for where 
    each TFBSs mapped onto various user-provided input sequences. Each time 
    Marina is ran, one "TFBS Coordinates" file can be saved. This script 
    enables such files to be "massaged" into a matrix. Each cell in the matrix 
    represents the abundance of the TFBS in the respective user-provided 
    sequence. Such an abundance matrix could therefore be used as input in 
    classification tasks such as regression or SVM tasks.
    '''
    
    parser = argparse.ArgumentParser(description = desc, add_help = False)
    param_reqd = parser.add_argument_group('Required')
    param_opts = parser.add_argument_group('Optional')
    param_reqd.add_argument('-in', required=True, metavar='', nargs='+',
                            help='One or more Marina-saved "TFBS Coordinate" files [na]')
    param_reqd.add_argument('-out', metavar='', type=str, default='out_matrix.tab',
                            help='Matrix output filename [out_matrix.tab]')
    param_opts.add_argument('-h','--help', action='help', 
                            help='Show this help screen and exit')
    args = vars(parser.parse_args())
    factory = MergerFactory(files = args['in'])
    factory.parse_files()
    factory.build_skeleton()